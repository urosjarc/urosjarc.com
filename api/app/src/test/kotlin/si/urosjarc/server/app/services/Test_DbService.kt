package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import si.urosjarc.server.app.base.App
import si.urosjarc.server.core.domain.Naloga
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
    fun naloge() {
        val naloga = Naloga(ime = "naloga")
        service.commit {
            service.seed()
            service.zvezek.post(naloga)
            for (n in service.zvezek.get(0)) {
                println(n)
            }
            service.drop()
        }
    }
}
