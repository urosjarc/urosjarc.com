package si.urosjarc.server.api.extend

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import api.configureRouting

fun ApplicationTestBuilder.test_client(): HttpClient {
    this.application {
        this.configureRouting()
    }
    return this.createClient {
        this.install(ContentNegotiation) {
            this.json(Json {
                this.isLenient = true
                this.allowSpecialFloatingPointValues = true
            })
        }
        this.install(Resources)
    }
}
