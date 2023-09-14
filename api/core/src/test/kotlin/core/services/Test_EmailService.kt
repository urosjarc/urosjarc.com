package core.services

import core.use_cases.Ustvari_templejt
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_EmailService : KoinTest {

    val log = logger()
    val service: EmailService by this.inject()
    val ustvari_template: Ustvari_templejt by this.inject()

    @BeforeEach
    fun before_each() {
        core.base.App.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        core.base.App.resetiraj_DI()
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
                    actual = r.email
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
            assertTrue(this.service.obstaja(email = "info@$it"), it)
        }
    }

    @Test
    fun `obstaja, false`() {
        mutableListOf(
            "info@programerskiklub.si",
            "jar.fmf@gmail.si",
        ).forEach {
            assertFalse(this.service.obstaja(email = it), it)
        }
    }

    @Test
    fun poslji_email() {
        val template = ustvari_template.email_potrditev_prejema_kontaktnega_obrazca(
            "Uroš",
            priimek = "Jarc",
            telefon = "051240885",
            email = "jar.fmf@gmail.com",
            vsebina = "Pozdravljeni! Potreboval bi pomoč pri programiranju mi lahko lepo prosim pomagate!"
        )
        this.service.poslji_email(
            fromName = "Uros Jarc",
            from = "info@urosjarc.com",
            to = "jar.fmf@gmail.com",
            subject = "Uroš Jarc | Vaš kontakt je bil sprejet!",
            html = template.html
        )
    }
}
