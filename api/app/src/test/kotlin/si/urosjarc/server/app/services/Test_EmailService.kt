package si.urosjarc.server.app.services

import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import app.base.App
import app.services.EmailService
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_EmailService : KoinTest {

    val log = logger()
    val service: EmailService by this.inject()

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
            " info@programerski-klub.si    ",
            "  jar.fmf@gmail.com  ",
        ).forEach {
            when (val r = this.service.formatiraj(email = it)) {
                is EmailService.RezultatEmailFormatiranja.DATA -> assertEquals(
                    expected = it.trim(),
                    actual = r.email.toString()
                )

                else -> fail(it)
            }
        }
    }

    @Test
    fun `formatiraj, rezultat fail`() {
        mutableListOf(
            " info@progra@merski-klub.ad    ",
            "  jar.fmf.mail.com  ",
        ).forEach {
            when (this.service.formatiraj(email = it)) {
                is EmailService.RezultatEmailFormatiranja.DATA -> fail(it)
                else -> {}
            }
        }
    }

    @Test
    fun `obstaja, true`() {
        mutableListOf(
            "programerski-klub.si",
            "gmail.co", "yahoo.com", "hotmail.com", "aol.com", "hotmail.co.uk", "hotmail.fr",
            "msn.com", "yahoo.fr", "wanadoo.fr", "orange.fr", "comcast.net", "yahoo.co.uk",
            "yahoo.com.br", "yahoo.co.in", "live.com", "rediffmail.com", "free.fr", "gmx.de",
            "web.de", "yandex.ru", "ymail.com", "libero.it", "outlook.com", "uol.com.br",
            "bol.com.br", "mail.ru", "cox.net", "hotmail.it", "sfr.fr",
            "live.fr", "verizon.net", "live.co.uk", "googlemail.com", "yahoo.es", "ig.com.br",
            "live.nl", "bigpond.com", "terra.com.br", "yahoo.it",
        ).forEach {
            val formatiran_email = EmailService.FormatiranEmail(value = "info@$it")
            assertTrue(this.service.obstaja(email = formatiran_email), it)
        }
    }

    @Test
    fun `obstaja, false`() {
        mutableListOf(
            "info@programerskiklub.si",
            "jar.fmf@gmail.si",
        ).forEach {
            val formatiran_email = EmailService.FormatiranEmail(value = it)
            assertFalse(this.service.obstaja(email = formatiran_email), it)
        }
    }

    @Test
    fun poslji_email() {
        log.error("Testing")
        this.service.poslji_email(
            from = "info@urosjarc.com",
            to = "info@urosjarc.com",
            subject = "subjekt",
            html = "html"
        )
    }
}
