package api

import base.Env
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
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
