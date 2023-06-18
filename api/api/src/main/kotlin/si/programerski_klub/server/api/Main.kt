package si.programerski_klub.server.api

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import si.programerski_klub.plugins.configureRouting
import si.programerski_klub.server.app.base.Env

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
