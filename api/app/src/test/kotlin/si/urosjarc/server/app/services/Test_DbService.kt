package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import app.base.App
import app.services.DbService
import core.domain.Oseba

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_DbService : KoinTest {

    val log = logger()
    val service: DbService by this.inject()

    @BeforeEach
    fun before_each() {
        App.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        App.resetiraj_DI()
    }

    @Test
    fun `nafilaj bazo`() {
        this.`sprazni bazo`()
        this.service.nafilaj()
    }

    @Test
    fun `sprazni bazo`() {
        this.service.sprazni()
    }

    @Test
    fun get_profil() {
        this.service.dobi<Oseba>(stran = 0).first().let {
            it._id?.let { it1 -> this.service.osebaRepo.profil(it1) }
        }
    }
}
