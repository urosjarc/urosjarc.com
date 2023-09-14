package api

import core.base.Env
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.apache.logging.log4j.kotlin.logger

fun main() {
    logger("main").info("Kotlin: ${KotlinVersion.CURRENT}")
    embeddedServer(
        Netty,
        host = "127.0.0.1",
        port = Env.PORT,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    this.configureRouting()
}
