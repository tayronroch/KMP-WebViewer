# KPM WebViewer

Aplicativo Android em Kotlin que carrega o sistema web da frota.

## 📱 Funcionalidades

- Carrega a página web: https://frota2.directtelecom.com.br
- Suporte completo a JavaScript
- Navegação interna no WebView
- Botão "Voltar" do Android funciona para navegar entre páginas

## 🔧 Configuração

### Pré-requisitos

- Android Studio (Arctic Fox ou superior)
- JDK 8 ou superior
- Gradle 8.2 ou superior
- SDK Android 24 ou superior

### Como compilar

1. Clone o repositório
2. Abra o projeto no Android Studio
3. Aguarde o Gradle sincronizar as dependências
4. Compile e execute no emulador ou dispositivo físico

```bash
./gradlew assembleDebug
```

## 📦 Estrutura do Projeto

```
app/
├── src/
│   └── main/
│       ├── java/com/example/meuwebviewapp/
│       │   └── MainActivity.kt
│       ├── res/
│       │   ├── layout/
│       │   │   └── activity_main.xml
│       │   └── values/
│       │       ├── strings.xml
│       │       ├── colors.xml
│       │       └── themes.xml
│       └── AndroidManifest.xml
└── build.gradle.kts
```

## 🔐 Permissões

O aplicativo requer as seguintes permissões:
- `INTERNET` - Para acessar a URL do sistema web
- `ACCESS_NETWORK_STATE` - Para verificar o estado da conexão

## 🚀 Versão

- **Versão**: 1.0
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## 📄 Licença

Este projeto é privado e de uso interno.
