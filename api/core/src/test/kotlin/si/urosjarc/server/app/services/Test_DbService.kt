package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import si.urosjarc.server.core.base.App
import si.urosjarc.server.core.domain.Entiteta
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.services.DbService

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
        this.service.nafilaj()
    }

    @Test
    fun `sprazni bazo`() {
        this.service.sprazni()
    }

    @Test
    fun insert_one() {
        val entity = Oseba(ime="asdf", priimek = "asdf", username = "asdf", tip = Oseba.Tip.UCENEC)
        println("$entity  ===================")
        this.service.ustvari(entity)
        println(entity)

    }
    @Test
    fun get_one() {
        for(o in this.service.dobi<Oseba>(0)){
            println(o)
        }
    }
}
