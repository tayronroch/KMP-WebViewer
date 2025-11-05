package com.example.webviewr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * WebViewer App - Carrega sistemas web como um app nativo
 *
 * Para configurar a URL, edite o arquivo AppConfig.kt
 */
@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        // WebView puro em tela cheia, sem nenhuma interface extra
        // Sem Surface para evitar backgrounds adicionais
        WebView(
            url = AppConfig.APP_URL,
            modifier = Modifier.fillMaxSize()
        )
    }
}