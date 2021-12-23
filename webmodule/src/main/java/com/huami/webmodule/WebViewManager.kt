package com.huami.webmodule

import android.annotation.SuppressLint
import android.content.Context
import android.content.MutableContextWrapper
import android.graphics.Color
import android.os.Build
import android.os.Looper
import com.tencent.smtt.export.external.interfaces.IX5WebSettings
import com.tencent.smtt.sdk.CookieManager
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView

/**
 * @date 2021/12/23
 * @author hezhanghe@zepp.com
 * @desc WebView 管理类
 */
class WebViewManager private constructor() {

    /**
     * 由于 WebView 需要和对应的 Activity.context 实例进行绑定
     * 为保证预加载的 WebView Context 和最终展示的 Context 保持一致。通过 MutableContextWrapper 解决
     *
     * MutableContextWrap 允许替换他的 baseContext
     * 则可以传 applicationContext 进行预加载，实际调用时传入 Activity.Context 进行替换
     */
    companion object {
        @Volatile
        private var INSTANCE: WebViewManager? = null

        private fun instance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: WebViewManager().also { INSTANCE = it }
        }

        fun prepare(context: Context) {
            instance().prepare(context)
        }

        fun obtain(context: Context): WebView {
            return instance().obtain(context)
        }

        fun recycle(webView: WebView) {
            instance().recycle(webView)
        }

        fun destroy() {
            instance().destroy()
        }
    }
    private val webViewCache = ArrayList<WebView>(1)

    private fun obtain(context: Context): WebView {
        if(webViewCache.isEmpty()){
            webViewCache.add(create(MutableContextWrapper(context)))
        }

        val webView = webViewCache.removeFirst()
        val contextWrapper = webView.context as MutableContextWrapper
        contextWrapper.baseContext = context
        webView.clearHistory()
        webView.resumeTimers()
        return webView
    }


    //    使用 IdleHandler 执行预创建
    private fun prepare(context: Context) {
        if (webViewCache.isEmpty()) {
            Looper.myQueue().addIdleHandler {
                webViewCache.add(create(MutableContextWrapper(context)))
                false
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun create(context: Context): WebView {
        val webView = WebView(context).apply {
            this.setBackgroundColor(Color.TRANSPARENT)
            this.view.setBackgroundColor(Color.TRANSPARENT)
            this.overScrollMode = WebView.OVER_SCROLL_NEVER
            this.view.overScrollMode = WebView.OVER_SCROLL_NEVER
        }
        val webSetting = webView.settings.apply {
            this.allowFileAccess = true
            this.setAppCacheEnabled(true)
            this.cacheMode = WebSettings.LOAD_DEFAULT
            this.domStorageEnabled = true
            this.setGeolocationEnabled(true)
            this.javaScriptEnabled = true
            this.loadWithOverviewMode = true
            this.setSupportZoom(true)
            this.displayZoomControls = false
            this.useWideViewPort = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
            }
        }
        webView.settingsExtension?.apply {
            setContentCacheEnable(true)
            setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY)
        }
        return webView
    }


}