package si.urosjarc.server.app.services

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import app.base.App
import app.services.TelefonService
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_PhoneService : KoinTest {

    val service: TelefonService by this.inject()

    @BeforeEach
    fun before_each() {
        App.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        App.resetiraj_DI()
    }

    @Test
    fun `formatiraj, rezultat ok`() {
        mutableListOf(
            "051240885",
            "+386051240885",
            "+386/051-240 885",
            "386/051-240 885",
            "051-240/885",
        ).forEach {
            when (val r = this.service.formatiraj(telefon = it)) {
                is TelefonService.FormatirajRezultat.DATA -> assertEquals(
                    expected = "+38651240885",
                    actual = r.telefon.toString()
                )

                else -> fail(it)
            }
        }
    }

    @Test
    fun `formatiraj, rezultat fail`() {
        mutableListOf(
            "1240885",
            "0a5 1 2 4 0 8 8 5",
        ).forEach {
            when (this.service.formatiraj(telefon = it)) {
                is TelefonService.FormatirajRezultat.WARN_TELEFON_NI_PRAVILNE_OBLIKE -> {}
                else -> fail(it)
            }
        }
    }

    @Test
    fun `obstaja, true`() {
        val formatiran_telefon = TelefonService.FormatiranTelefon(value = "+386051240885")
        assertTrue(this.service.obstaja(telefon = formatiran_telefon))
    }

    @Test
    fun `obstaja, false`() {
        val formatiran_telefon = TelefonService.FormatiranTelefon(value = "051240885")
        assertFalse(this.service.obstaja(telefon = formatiran_telefon))
    }

    @Test
    fun poslji_sms() {
        val formatiran_telefon = TelefonService.FormatiranTelefon(value = "+38651240885")
        when (val r = this.service.poslji_sms(telefon = formatiran_telefon, "Hello World!")) {
            is TelefonService.RezultatSmsPosiljanja.ERROR_SMS_NI_POSLAN -> fail(r.info)
            TelefonService.RezultatSmsPosiljanja.PASS -> {}
        }
    }
}
