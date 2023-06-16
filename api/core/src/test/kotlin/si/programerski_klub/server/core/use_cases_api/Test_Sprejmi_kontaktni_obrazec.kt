import org.junit.jupiter.api.*
import org.koin.test.KoinTest
import org.koin.test.inject
import si.programerski_klub.server.core.base.Entiteta
import si.programerski_klub.server.core.domain.obvescanje.KontaktniObrazec
import si.programerski_klub.server.core.extend.pripravi_DI
import si.programerski_klub.server.core.extend.resetiraj_DI
import si.programerski_klub.server.core.use_cases_api.Sprejmi_kontakti_obrazec
import kotlin.test.assertEquals
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class `sprejmi kontaktni obrazec` : KoinTest {

    // Lazy inject property
    val sprejmni_kontaktni_obrazec: Sprejmi_kontakti_obrazec by this.inject()
    val kontaktni_obrazec = Entiteta.random<KontaktniObrazec>()

    @BeforeEach
    fun before_each() {
        this.pripravi_DI()
    }

    @AfterEach
    fun after_each() {
        this.resetiraj_DI()
    }

    @Test
    fun `sprejme kontakt`() {
        // directly request an instance
        when (val r = this.sprejmni_kontaktni_obrazec.zdaj(kontaktni_obrazec = this.kontaktni_obrazec)) {
            Sprejmi_kontakti_obrazec.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> fail()
            Sprejmi_kontakti_obrazec.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> fail()
            Sprejmi_kontakti_obrazec.Rezultat.FATAL_KONTAKTNI_OBRAZEC_SE_NI_SHRANIL -> fail()
            is Sprejmi_kontakti_obrazec.Rezultat.DATA -> {
                assertEquals(r.kontaktni_obrazec, this.kontaktni_obrazec)
            }
        }

    }
}
