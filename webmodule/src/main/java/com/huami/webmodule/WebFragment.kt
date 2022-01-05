package com.huami.webmodule

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.tencent.smtt.sdk.WebView

/**
 * Created by hezhanghe on 2021/12/27.
 * github: https://github.com/HadesHe
 */
class WebFragment : Fragment(R.layout.fragment_web) {

    companion object {
        const val WEB_FRAGMENR_URL = "WEB_FRAGMENR_URL"
    }

    private var browseBtn: View? = null
    private var refreshBtn: View? = null
    private var forwardBtn: View? = null
    private var backBtn: View? = null
    private lateinit var webViewHelper: WebViewHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    private fun initView(view: View) {


        val webContainer = view.findViewById<FrameLayout>(R.id.web_container)
        backBtn = view.findViewById<View>(R.id.black)
        forwardBtn = view.findViewById<View>(R.id.forward)
        refreshBtn = view.findViewById<View>(R.id.refresh)
        forwardBtn = view.findViewById<View>(R.id.forward)
        browseBtn = view.findViewById<View>(R.id.browse)

        webViewHelper = WebViewHelper.with(webContainer)
            .injectVConsole(false)
            .setOnPageChangedListener(object : WebViewHelper.OnPageChangedListener {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                }
            })

        kotlin.runCatching {
            requireArguments().getString(WEB_FRAGMENR_URL)?.let { url ->
                webViewHelper.loadUrl(url)
            }
        }

        val onBackPressed =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (!webViewHelper.canGoBack()) {
                    this.isEnabled = false
                    requireActivity().onBackPressed()
                }
            }


        backBtn?.setOnClickListener {
            if (!webViewHelper.canGoBack()) {
                onBackPressed.isEnabled = false
                requireActivity().onBackPressed()
            }
        }

        forwardBtn?.setOnClickListener {
            webViewHelper.canGoForward()
        }

        refreshBtn?.setOnClickListener {
            webViewHelper.reload()
        }


    }


}