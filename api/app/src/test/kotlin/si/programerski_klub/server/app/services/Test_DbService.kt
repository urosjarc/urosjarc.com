package si.programerski_klub.server.app.services

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import si.programerski_klub.server.app.base.App
import si.programerski_klub.server.core.base.Entiteta
import si.programerski_klub.server.core.domain.obvescanje.KontaktniObrazec
import si.programerski_klub.server.core.domain.placevanje.*
import si.programerski_klub.server.core.services.DbService
import kotlin.test.assertEquals
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class Test_DbService : KoinTest {

    val service: DbService by this.inject()

    @BeforeEach
    fun before_each() {
        App.pripravi_DI()
        this.service.izbrisi_vse()
    }

    @AfterEach
    fun after_each() {
        App.resetiraj_DI()
    }

    @Test
    fun `shrani narocilo`() {
        val narocilo = Entiteta.random<Narocilo>()

        when (val r = this.service.shrani_narocilo(narocilo = narocilo)) {
            is DbService.RezultatShranitve.DATA -> {
                // - Preveri ali je shranjena narocilo enaka poslanemu
                assertEquals(expected = r.data, actual = narocilo)

                // - Preveri ali se je narocilo shranilo.
                when (this.service.narocilo(id = r.data.id)) {
                    is DbService.RezultatIdentifikacije.ERROR -> fail()
                    is DbService.RezultatIdentifikacije.DATA -> {}
                }

                // - Preveri ali se je prejemnik shranil.
                when (this.service.oseba(id = r.data.prejemnik.id)) {
                    is DbService.RezultatIdentifikacije.ERROR -> fail()
                    is DbService.RezultatIdentifikacije.DATA -> {}
                }

                // - Preveri ali se je placnik shranil.
                r.data.placnik?.run {
                    when (this@Test_DbService.service.oseba(id = this.id)) {
                        is DbService.RezultatIdentifikacije.ERROR -> fail()
                        is DbService.RezultatIdentifikacije.DATA -> {}
                    }
                }
            }

            else -> fail()
        }
    }

    @Test
    fun `shrani narocnino`() {
        val narocnina = Entiteta.random<Narocnina>()

        when (val r = this.service.shrani_narocnino(narocnina = narocnina)) {
            is DbService.RezultatShranitve.DATA -> {
                // - Preveri ali je narocnina enaka poslani.
                assertEquals(expected = r.data, actual = narocnina)

                // - Preveri ali se je narocnina shranila.
                when (this.service.narocnina(id = r.data.id)) {
                    is DbService.RezultatIdentifikacije.ERROR -> fail()
                    is DbService.RezultatIdentifikacije.DATA -> {}
                }
            }

            else -> fail()
        }
    }

    @Test
    fun `shrani kontaktni obrazec`() {
        val kontaktni_obrazec = Entiteta.random<KontaktniObrazec>()

        when (val r = this.service.shrani_kontaktni_obrazec(kontaktni_obrazec = kontaktni_obrazec)) {
            is DbService.RezultatShranitve.DATA -> {
                //- Preveri ali je poslan kontaktni obrazec enak poslanemu.
                assertEquals(expected = r.data, actual = kontaktni_obrazec)

                // - Preveri ali se je kontaktni obrazec shranil
                when (this.service.kontaktni_obrazec(id = r.data.id)) {
                    is DbService.RezultatIdentifikacije.ERROR -> fail()
                    is DbService.RezultatIdentifikacije.DATA -> {}
                }
            }

            else -> fail()
        }
    }

}
