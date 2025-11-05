package com.example.webviewr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.browser.window

/**
 * WebAssembly implementation of WebView
 * Redirects to the configured URL since running a WebView inside a browser is redundant
 */
@Composable
actual fun WebView(
    url: String,
    modifier: Modifier,
    onBack: (() -> Boolean)?,
    onLoadingChange: ((Boolean) -> Unit)?
) {
    LaunchedEffect(url) {
        onLoadingChange?.invoke(false)
        // Redirect to the URL
        window.location.href = url
    }

    // Show a message while redirecting
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text("Redirecionando para $url...")
        }
    }
}
