package com.huami.webmodule

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebSettings.FORCE_DARK_OFF
import android.webkit.WebSettings.FORCE_DARK_ON
import android.webkit.WebView.HitTestResult.IMAGE_TYPE
import android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.huami.webmodule.http.HttpRequest
import com.huami.webmodule.http.download
import com.huami.webmodule.util.CacheUtils
import com.huami.webmodule.util.injectVConsoleJs
import com.huami.webmodule.util.isNightMode
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.*
import kotlinx.coroutines.runBlocking
import okio.ByteString.Companion.encodeUtf8
import java.io.File

/**
 * @date 2021/12/24
 * @author hezhanghe@zepp.com
 * @desc
 */
class WebViewHelper private constructor(parent: ViewGroup) {

    companion object {
        fun with(parent: ViewGroup): WebViewHelper {
            return WebViewHelper(parent)
        }
    }

    private var onPageChangedListener: OnPageChangedListener? = null
    private val webView = WebViewManager.obtain(parent.context)
    private var injectState = false
    private var injectVConsole = false
    private var originalUrl = "about:blank"

    init {
        parent.addView(webView, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri = request?.url
                if (view != null && uri != null && !("http" == uri.scheme || "https" == uri.scheme)) {

                    kotlin.runCatching {
                        view.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }.onFailure {
                        it.printStackTrace()
                    }
                    return true
                }
                return false
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                if (view != null && request != null) {
                    when {
                        canAssetsResource(request) -> {
                            return assetsResourceRequest(view.context, request)
                        }
                        canCacheResource(request) -> {
                            return cacheResourceRequest(view.context, request)
                        }
                    }

                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
                super.onPageStarted(p0, p1, p2)
                onPageChangedListener?.onPageStarted(p0, p1, p2)
                injectState = false
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                onPageChangedListener?.onPageFinished(p0, p1)
                injectState = false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                onPageChangedListener?.onProgressChanged(p0, p1)
                if (p1 > 80 && injectVConsole && injectState.not()) {
                    p0?.apply {
                        evaluateJavascript(context.injectVConsoleJs()) {}
                    }
                    injectState = true
                }
            }
        }
        webView.setDownloadListener { url, _, _, _, _ ->
            kotlin.runCatching {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                webView.context.startActivity(intent)
            }.onFailure {
                it.printStackTrace()
            }
        }
        webView.setOnLongClickListener {
            val result = webView.hitTestResult
            when (result.type) {
                IMAGE_TYPE, SRC_IMAGE_ANCHOR_TYPE -> {
                    println(result.extra)
                    true
                }
                else -> false
            }
        }
        val isAppDarkMode = webView.context.isNightMode()
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            QbSdk.forceSysWebView()
            val view = webView.view
            if (view is android.webkit.WebView) {
                val forceDarkMode = if (isAppDarkMode) FORCE_DARK_ON else FORCE_DARK_OFF
                WebSettingsCompat.setForceDark(view.settings, forceDarkMode)
            }
        } else {
            QbSdk.unForceSysWebView()
            webView.setDayOrNight(!isAppDarkMode)
        }
    }

    fun setOnPageChangedListener(onPageChangedListener: OnPageChangedListener?): WebViewHelper {
        this.onPageChangedListener = onPageChangedListener
        return this
    }

    fun canGoBack(): Boolean {
        val canBack = webView.canGoBack()
        if (canBack) webView.goBack()
        val backForwardList = webView.copyBackForwardList()
        val currentIndex = backForwardList.currentIndex
        if (currentIndex == 0) {
            val currentUrl = backForwardList.currentItem.url
            val currentHost = Uri.parse(currentUrl).host
            //栈底不是链接则直接返回
            if (currentHost.isNullOrBlank()) return false
            //栈底链接不是原始链接则直接返回
            if (originalUrl != currentUrl) return false
        }
        return canBack
    }

    fun canGoForward(): Boolean {
        val canForward = webView.canGoForward()
        if (canForward) webView.goForward()
        return canForward
    }

    fun loadUrl(url: String) {
        webView.loadUrl(url)
        originalUrl = url
    }

    fun reload() {
        webView.reload()
    }

    fun onResume() {
        webView.onResume()
    }

    fun onPause() {
        webView.onPause()
    }

    fun onDestroyView() {
        WebViewManager.recycle(webView)
    }

    private fun assetsResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        return kotlin.runCatching {
            val url = webRequest.url.toString()
            val filenameIndex = url.lastIndexOf("/") + 1
            val filename = url.substring(filenameIndex)
            val suffixIndex = url.lastIndexOf(".")
            val suffix = url.substring(suffixIndex + 1)
            val webResourceResponse = WebResourceResponse()
            webResourceResponse.mimeType = getMimeTypeFromUrl(url)
            webResourceResponse.encoding = "UTF-8"
            webResourceResponse.data = context.assets.open("$suffix/$filename")
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            webResourceResponse
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }

    private fun canCacheResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = getExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif"
                || extension == "jpeg" || extension == "jpg" || extension == "png"
                || extension == "svg" || extension == "webp" || extension == "css"
                || extension == "js" || extension == "json" || extension == "eot"
                || extension == "otf" || extension == "ttf" || extension == "woff"
    }

    private fun cacheResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        try {
            val url = webRequest.url.toString()
            val cachePath = CacheUtils.getCacheDirPath(context, "web_cache")
            val filePathName = cachePath + File.separator + url.encodeUtf8().md5().hex()
            val file = File(filePathName)
            if (!file.exists() || !file.isFile) {
                runBlocking {
                    download(HttpRequest(url).apply {
                        webRequest.requestHeaders.forEach { putHeader(it.key, it.value) }
                    }, filePathName)
                }
            }
            if (file.exists() && file.isFile) {
                val webResourceResponse = WebResourceResponse()
                webResourceResponse.mimeType = getMimeTypeFromUrl(url)
                webResourceResponse.encoding = "UTF-8"
                webResourceResponse.data = file.inputStream()
                webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
                return webResourceResponse
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getExtensionFromUrl(url: String): String {
        try {
            if (url.isNotBlank() && url != "null") {
                val extension = url
                    .substringBeforeLast('#') // Strip the fragment.
                    .substringBeforeLast('?') // Strip the query.
                    .substringAfterLast('/') // Get the last path segment.
                    .substringAfterLast('.', missingDelimiterValue = "") // Get the file extension.
                return MimeTypeMap.getFileExtensionFromUrl(extension)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getMimeTypeFromUrl(url: String): String {
        try {
            val extension = getExtensionFromUrl(url)
            if (extension.isNotBlank() && extension != "null") {
                if (extension == "json") {
                    return "application/json"
                }
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "*/*"
    }

    private fun canAssetsResource(request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return url.startsWith("file:///android_asset/")
    }

    interface OnPageChangedListener {
        fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
        fun onPageFinished(view: WebView?, url: String?)
        fun onProgressChanged(view: WebView?, newProgress: Int)
    }

}