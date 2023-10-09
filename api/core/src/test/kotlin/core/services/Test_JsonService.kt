package core.services

import core.base.App
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_JsonService : KoinTest {

    val log = logger()
    val service: JsonService by this.inject()

    @BeforeEach
    fun before_each() {
        App.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        App.resetiraj_DI()
    }

}
