package si.urosjarc.server.api.routes

import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import si.urosjarc.server.api.extend.test_client
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.domain.placevanje.Narocilo
import kotlin.test.assertEquals

class narocilo_test {

    @Test
    fun `POST narocilo`() = testApplication {
        val body = Entiteta.nakljucni<Narocilo>()

        this.test_client().post(narocila()) {
            this.contentType(ContentType.Application.Json)
            this.setBody(body)
        }.apply {
            println(this.bodyAsText())
            assertEquals(this.status, HttpStatusCode.OK)
        }
    }
}
