package com.example.webviewr

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Multiplatform WebView component
 * Displays web content using platform-specific implementations
 *
 * @param url The URL to load in the WebView
 * @param modifier Modifier for the WebView
 * @param onBack Optional callback for back navigation (returns true if can go back)
 * @param onLoadingChange Optional callback when loading state changes
 */
@Composable
expect fun WebView(
    url: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Boolean)? = null,
    onLoadingChange: ((Boolean) -> Unit)? = null
)
