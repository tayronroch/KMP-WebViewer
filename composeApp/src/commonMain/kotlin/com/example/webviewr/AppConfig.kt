package com.example.webviewr

/**
 * Configuração do aplicativo WebViewer
 *
 * Para mudar a URL que o app carrega, basta alterar o valor da constante APP_URL abaixo
 */
object AppConfig {
    /**
     * URL que será carregada automaticamente quando o app iniciar
     *
     * Exemplos:
     * - Sistema de Frota: "https://frota2.directtelecom.com.br/"
     * - Google: "https://www.google.com"
     * - Qualquer outro sistema web: "https://seu-sistema.com.br"
     */
    const val APP_URL = "https://frota2.directtelecom.com.br/"

    /**
     * Nome do aplicativo exibido na barra superior
     */
    const val APP_NAME = "WebViewer"
}
