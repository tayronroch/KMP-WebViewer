# KPM-WebViewer

Este é um projeto **Kotlin Multiplataforma** que tem como objetivo exibir uma página da web em um aplicativo nativo para **Android e iOS**. O projeto é configurado para carregar uma URL específica, que pode ser facilmente alterada no arquivo `AppConfig.kt`.

## Configuração

Para alterar a URL que o aplicativo carrega, abra o arquivo `composeApp/src/commonMain/kotlin/com/example/webviewr/AppConfig.kt` e modifique a constante `APP_URL`.

```kotlin
object AppConfig {
    /**
     * URL que será carregada automaticamente quando o app iniciar
     *
     * Exemplos:
     * - Sistema de Frota: "https://frota2.directtelecom.com.br/"
     * - Google: "https://www.google.com"
     * - Qualquer outro sistema web: "https://seu-sistema.com.br"
     */
    const val APP_URL = "https://seu-sistema.com.br"

    /**
     * Nome do aplicativo exibido na barra superior
     */
    const val APP_NAME = "WebViewer"
}
```

Este é um projeto Kotlin Multiplataforma visando Android e iOS.

- [/composeApp](./composeApp/src) é para código que será compartilhado entre seus aplicativos Compose Multiplataforma.
  Ele contém várias subpastas:

  - [commonMain](./composeApp/src/commonMain/kotlin) é para código que é comum para todos os alvos.
  - Outras pastas são para código Kotlin que será compilado apenas para a plataforma indicada no nome da pasta.
    Por exemplo, se você quiser usar o CoreCrypto da Apple para a parte iOS do seu aplicativo Kotlin,
    a pasta [iosMain](./composeApp/src/iosMain/kotlin) seria o lugar certo para tais chamadas.
    Da mesma forma, se você quiser editar a parte específica do Desktop (JVM), a pasta [jvmMain](./composeApp/src/jvmMain/kotlin)
    é o local apropriado.

- [/iosApp](./iosApp/iosApp) contém aplicativos iOS. Mesmo que você esteja compartilhando sua interface do usuário com o Compose Multiplataforma,
  você precisa deste ponto de entrada para seu aplicativo iOS. É aqui também que você deve adicionar o código SwiftUI para o seu projeto.

### Compile e execute o aplicativo Android

Para compilar e executar a versão de desenvolvimento do aplicativo Android, use a configuração de execução do widget de execução
na barra de ferramentas do seu IDE ou compile-o diretamente do terminal:

- no macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- no Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Compile e execute o aplicativo Web

Para compilar e executar a versão de desenvolvimento do aplicativo da web, use a configuração de execução do widget de execução
na barra de ferramentas do seu IDE ou execute-o diretamente do terminal:

- para o alvo Wasm (mais rápido, navegadores modernos):
  - no macOS/Linux
    ```shell
    ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
    ```
  - no Windows
    ```shell
    .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
    ```
- para o alvo JS (mais lento, suporta navegadores mais antigos):
  - no macOS/Linux
    ```shell
    ./gradlew :composeApp:jsBrowserDevelopmentRun
    ```
  - no Windows
    ```shell
    .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
    ```

### Compile e execute o aplicativo iOS

Para compilar e executar a versão de desenvolvimento do aplicativo iOS, use a configuração de execution do widget de execução
na barra de ferramentas do seu IDE ou abra o diretório [/iosApp](./iosApp) no Xcode e execute-o a partir daí.

---

Saiba mais sobre [Kotlin Multiplataforma](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplataforma](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

Agradeceríamos seus comentários sobre Compose/Web e Kotlin/Wasm no canal público do Slack [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
Se você enfrentar algum problema, por favor, reporte-o no [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
