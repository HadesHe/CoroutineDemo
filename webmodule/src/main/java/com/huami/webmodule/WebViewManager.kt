package com.huami.webmodule

import android.content.Context
import android.content.MutableContextWrapper
import android.os.Looper
import com.tencent.smtt.sdk.WebView

/**
 * @date 2021/12/23
 * @author hezhanghe@zepp.com
 * @desc WebView 管理类
 */
class WebViewManager private constructor() {

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

    //    使用 IdleHandler 执行预创建
    private fun prepare(context: Context) {
        if(webViewCache.isEmpty()){
            Looper.myQueue().addIdleHandler {
                webViewCache.add(create(MutableContextWrapper(context)))
                false
            }
        }
    }

    private fun create(context: Context): WebView {
        val webView = WebView(context)
        webView.view
        return webView
    }


}