package si.urosjarc.server.app.use_cases

import base.App
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import use_cases.Sinhroniziraj_bazo_zvezkov

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_Sinhroniziraj_bazo_zvezkov : KoinTest {

    val log = logger()
    val use_case: Sinhroniziraj_bazo_zvezkov by this.inject()

    @BeforeEach
    fun before_each() {
        App.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        App.resetiraj_DI()
    }

    @Test
    fun zdaj() {
        this.use_case.zdaj()
    }

}
