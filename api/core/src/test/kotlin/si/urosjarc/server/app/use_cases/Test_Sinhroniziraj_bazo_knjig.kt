package si.urosjarc.server.app.use_cases

import base.App
import kotlinx.serialization.encodeToString
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import services.DbService
import services.JsonService
import use_cases.Sinhroniziraj_bazo_knjig
import use_cases.Ustvari_testne_podatke

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_Sinhroniziraj_bazo_knjig : KoinTest {

    val log = logger()
    val use_case: Sinhroniziraj_bazo_knjig by this.inject()
    val db: DbService by this.inject()
    val json: JsonService by this.inject()

    @BeforeEach
    fun before_each() {
        App.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        App.resetiraj_DI()
    }

    @Test
    fun `zdaj`() {
        this.use_case.zdaj(vse=true)
    }

}
