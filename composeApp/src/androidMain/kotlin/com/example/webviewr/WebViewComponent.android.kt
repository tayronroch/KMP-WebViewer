package com.example.webviewr

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.GeolocationPermissions
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Android implementation of WebView using android.webkit.WebView
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun WebView(
    url: String,
    modifier: Modifier,
    onBack: (() -> Boolean)?,
    onLoadingChange: ((Boolean) -> Unit)?
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                // ============================================
                // CONFIGURAÇÕES BÁSICAS DE JAVASCRIPT
                // ============================================
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true

                // ============================================
                // STORAGE E DATABASE
                // ============================================
                settings.domStorageEnabled = true
                settings.databaseEnabled = true

                // ============================================
                // CONFIGURAÇÕES DE VIEWPORT E LAYOUT
                // ============================================
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING

                // ============================================
                // ZOOM
                // ============================================
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false

                // ============================================
                // SEGURANÇA E CONTEÚDO MISTO
                // ============================================
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true

                // ============================================
                // CACHE E PERFORMANCE
                // ============================================
                settings.cacheMode = android.webkit.WebSettings.LOAD_DEFAULT

                // ============================================
                // MEDIA E GEOLOCALIZAÇÃO
                // ============================================
                settings.mediaPlaybackRequiresUserGesture = false
                settings.setGeolocationEnabled(true)

                // ============================================
                // USER AGENT - Simular navegador Chrome moderno
                // ============================================
                // User agent atualizado para compatibilidade com Next.js
                settings.userAgentString = "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Mobile Safari/537.36"

                // ============================================
                // RENDERING E HARDWARE
                // ============================================
                // Usa aceleração de hardware para melhor performance
                setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)

                // Background branco para evitar problemas de transparência
                setBackgroundColor(android.graphics.Color.WHITE)

                // ============================================
                // CONFIGURAÇÕES ADICIONAIS DE COMPATIBILIDADE
                // ============================================
                settings.setSupportMultipleWindows(true)
                settings.loadsImagesAutomatically = true
                settings.blockNetworkImage = false
                settings.blockNetworkLoads = false

                // Configurações adicionais para melhor compatibilidade
                settings.safeBrowsingEnabled = true
                settings.offscreenPreRaster = true

                // Habilita recursos modernos do WebView
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    settings.safeBrowsingEnabled = true
                }

                // Force enable GPU rasterization
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    settings.offscreenPreRaster = true
                }

                // Desabilita force dark mode para evitar problemas de renderização
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    settings.isAlgorithmicDarkeningAllowed = false
                }

                // Set WebViewClient to handle navigation and errors
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(
                        view: WebView?,
                        url: String?,
                        favicon: android.graphics.Bitmap?
                    ) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("WebView", "Page started loading: $url")
                        onLoadingChange?.invoke(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        Log.d("WebView", "Page finished loading: $url")

                        // JavaScript injection específico para Next.js
                        view?.evaluateJavascript("""
                            (function() {
                                console.log('=== Next.js WebView Initialization ===');
                                console.log('Document ready state:', document.readyState);
                                console.log('Body exists:', !!document.body);

                                // Detecta se é Next.js
                                var isNextJs = window.__NEXT_DATA__ !== undefined;
                                console.log('Is Next.js app:', isNextJs);

                                if (isNextJs) {
                                    console.log('Next.js version:', window.__NEXT_DATA__.buildId || 'unknown');
                                }

                                // Aguarda hidratação do Next.js/React completar
                                function waitForNextJsHydration() {
                                    var attempts = 0;
                                    var maxAttempts = 30; // 6 segundos (30 x 200ms)
                                    var lastElementCount = 0;
                                    var stableCount = 0;

                                    function check() {
                                        attempts++;
                                        var currentElementCount = document.getElementsByTagName('*').length;

                                        console.log('Hydration check ' + attempts + '/' + maxAttempts + ': ' + currentElementCount + ' elements');

                                        // Verifica se a contagem de elementos está estável
                                        if (currentElementCount === lastElementCount) {
                                            stableCount++;
                                        } else {
                                            stableCount = 0;
                                            lastElementCount = currentElementCount;
                                        }

                                        // Se estável por 3 checks consecutivos (600ms), considera completo
                                        if (stableCount >= 3) {
                                            console.log('Next.js hydration complete! Elements:', currentElementCount);

                                            // Força repaint final
                                            window.scrollBy(0, 1);
                                            window.scrollBy(0, -1);

                                            // Dispara evento customizado para sinalizar que está pronto
                                            if (typeof window.ReactNativeWebView !== 'undefined') {
                                                window.ReactNativeWebView.postMessage('HYDRATION_COMPLETE');
                                            }
                                            return;
                                        }

                                        if (attempts >= maxAttempts) {
                                            console.warn('Next.js hydration timeout - forcing render');
                                            window.scrollBy(0, 1);
                                            window.scrollBy(0, -1);
                                            return;
                                        }

                                        // Continua verificando
                                        setTimeout(check, 200);
                                    }

                                    check();
                                }

                                // Aguarda um pouco para Next.js começar a hidratar
                                if (isNextJs) {
                                    // Para Next.js, aguarda mais tempo antes de começar a verificar
                                    setTimeout(waitForNextJsHydration, 500);
                                } else {
                                    // Para outros apps React, começa mais cedo
                                    setTimeout(waitForNextJsHydration, 300);
                                }

                                return 'OK';
                            })();
                        """.trimIndent()) { result ->
                            Log.d("WebView", "JavaScript injection result: $result")
                        }

                        onLoadingChange?.invoke(false)
                    }

                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                        Log.d("WebView", "Page became visible: $url")

                        // Força um repaint quando a página se torna visível
                        view?.postDelayed({
                            view.invalidate()
                        }, 100)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        // Log apenas para main frame, ignore erros de recursos
                        if (request?.isForMainFrame == true) {
                            Log.e("WebView", "Error loading main frame: ${request.url} - ${error?.description}")
                        } else {
                            Log.w("WebView", "Error loading resource: ${request?.url}")
                        }
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        // Permite navegação dentro do WebView
                        return false
                    }
                }

                // Set WebChromeClient for complete JavaScript support and console logging
                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        consoleMessage?.let {
                            Log.d(
                                "WebViewConsole",
                                "${it.message()} -- From line ${it.lineNumber()} of ${it.sourceId()}"
                            )
                        }
                        return true
                    }

                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        Log.d("WebView", "Loading progress: $newProgress%")
                    }

                    // Handle permission requests from web content (camera, microphone, etc)
                    override fun onPermissionRequest(request: PermissionRequest?) {
                        request?.let {
                            Log.d("WebView", "Permission request: ${it.resources.joinToString()}")
                            // Grant all requested permissions
                            // Note: Android runtime permissions should be handled in MainActivity
                            it.grant(it.resources)
                        }
                    }

                    // Handle geolocation permission requests
                    override fun onGeolocationPermissionsShowPrompt(
                        origin: String?,
                        callback: GeolocationPermissions.Callback?
                    ) {
                        Log.d("WebView", "Geolocation permission request from: $origin")
                        callback?.invoke(origin, true, false)
                    }
                }

                // Load the URL
                Log.d("WebView", "Loading URL: $url")
                loadUrl(url)
            }
        },
        update = { webView ->
            if (webView.url != url) {
                Log.d("WebView", "Updating URL to: $url")
                webView.loadUrl(url)
            }
        }
    )

    // Handle back navigation
    DisposableEffect(onBack) {
        val callback = onBack
        onDispose { }
    }
}

/**
 * Extension function to check if WebView can go back
 * This can be used by the MainActivity to handle back button
 */
fun WebView.canNavigateBack(): Boolean = canGoBack()

/**
 * Extension function to navigate back in WebView
 */
fun WebView.navigateBack() {
    if (canGoBack()) {
        goBack()
    }
}
