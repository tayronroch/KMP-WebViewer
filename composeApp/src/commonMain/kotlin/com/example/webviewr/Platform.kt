package com.example.webviewr

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform