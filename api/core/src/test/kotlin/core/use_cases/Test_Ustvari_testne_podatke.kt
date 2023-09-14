package core.use_cases

import core.base.App
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_Ustvari_testne_podatke : KoinTest {

    val use_case: Ustvari_testne_podatke by this.inject()

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
        this.use_case.zdaj(vse = true)
    }

}
