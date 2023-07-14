package api

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import app.base.Env
import io.sentry.Sentry

fun main() {
    Sentry.init { options ->
        options.dsn = Env.SENTRY_URL
        // Set tracesSampleRate to 1.0 to capture 100% of transactions for performance monitoring.
        // We recommend adjusting this value in production.
        options.tracesSampleRate = 1.0
        // When first trying Sentry it's good to see what the SDK is doing:
        options.isDebug = true
    }
    embeddedServer(
        Netty,
        port = Env.PORT,
        module = Application::module,
        watchPaths = listOf("classes")
    ).start(wait = true)
}

fun Application.module() {
    this.configureRouting()
}
