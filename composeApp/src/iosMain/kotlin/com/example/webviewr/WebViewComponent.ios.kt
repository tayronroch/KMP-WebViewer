package com.example.webviewr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURL
import platform.WebKit.WKWebView
import platform.WebKit.WKNavigationDelegateProtocol
import platform.darwin.NSObject

/**
 * iOS implementation of WebView using WKWebView
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(
    url: String,
    modifier: Modifier,
    onBack: (() -> Boolean)?,
    onLoadingChange: ((Boolean) -> Unit)?
) {
    val webView = remember { WKWebView() }

    // Create navigation delegate
    val navigationDelegate = remember {
        object : NSObject(), WKNavigationDelegateProtocol {
            override fun webView(
                webView: WKWebView,
                didStartProvisionalNavigation: platform.WebKit.WKNavigation?
            ) {
                onLoadingChange?.invoke(true)
            }

            override fun webView(
                webView: WKWebView,
                didFinishNavigation: platform.WebKit.WKNavigation?
            ) {
                onLoadingChange?.invoke(false)
            }

            override fun webView(
                webView: WKWebView,
                didFailNavigation: platform.WebKit.WKNavigation?,
                withError: platform.Foundation.NSError
            ) {
                onLoadingChange?.invoke(false)
            }
        }
    }

    UIKitView(
        modifier = modifier,
        factory = {
            webView.apply {
                navigationDelegate = navigationDelegate

                // Enable JavaScript (similar to Android implementation)
                configuration.preferences.javaScriptEnabled = true

                // Load the URL
                val nsUrl = NSURL.URLWithString(url)
                if (nsUrl != null) {
                    val request = NSURLRequest.requestWithURL(nsUrl)
                    loadRequest(request)
                }
            }
        },
        update = { view ->
            val nsUrl = NSURL.URLWithString(url)
            if (nsUrl != null && view.URL?.absoluteString != url) {
                val request = NSURLRequest.requestWithURL(nsUrl)
                view.loadRequest(request)
            }
        }
    )

    DisposableEffect(onBack) {
        onDispose { }
    }
}
