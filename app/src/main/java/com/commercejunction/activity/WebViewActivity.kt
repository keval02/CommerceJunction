package com.commercejunction.activity

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.commercejunction.R
import com.commercejunction.constants.Global.keyWebView
import com.commercejunction.constants.Global.keyWebViewTitle
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    var webViewURL = ""
    var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_web_view)
        try {
            webViewURL = intent.getStringExtra(keyWebView)
            webViewURL = webViewURL.replace(" ", "%20")
            title = intent.getStringExtra(keyWebViewTitle)
            boardTV2.text = title
        } catch (e: Exception) {

        }

        board_back.setOnClickListener {
            onBackPressed()
        }

        webView.loadUrl(webViewURL)

        webView.setInitialScale(1)
        webView.settings.pluginState = WebSettings.PluginState.ON

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }


        val webSettings: WebSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.allowContentAccess = true
        webSettings.setEnableSmoothTransition(true)
        webSettings.loadsImagesAutomatically = true
        webSettings.loadWithOverviewMode = true
        webSettings.setSupportZoom(false)
        webSettings.useWideViewPort = true
        webSettings.setAppCacheEnabled(true)
        webSettings.setSupportMultipleWindows(true)
        webView.loadUrl(webViewURL)

    }


}