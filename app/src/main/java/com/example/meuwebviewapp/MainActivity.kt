package com.example.meuwebviewapp // Ajuste este pacote se o seu for diferente

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Define o layout que será usado por esta atividade
        setContentView(R.layout.activity_main)

        // 1. Encontra o WebView no layout pelo ID que definimos no XML
        webView = findViewById(R.id.webview)

        // 2. Configurações essenciais do WebView
        
        // Habilita o JavaScript (essencial para a maioria dos apps web modernos)
        webView.settings.javaScriptEnabled = true

        // 3. Define um WebViewClient
        // Isso faz com que os links clicados dentro do WebView abram no próprio
        // WebView, em vez de abrir no navegador padrão do celular.
        webView.webViewClient = WebViewClient()

        // 4. Carrega a URL do seu aplicativo web
        // !!! URL ATUALIZADA !!!
        webView.loadUrl("https://frota2.directtelecom.com.br")

        // 5. (Opcional, mas recomendado) Gerencia o botão "Voltar"
        // Isso faz com que o botão "Voltar" do Android navegue para a página
        // anterior no histórico do WebView, se houver.
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    // Se o WebView pode voltar, volte
                    webView.goBack()
                } else {
                    // Se não, execute a ação padrão (fechar o app)
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}