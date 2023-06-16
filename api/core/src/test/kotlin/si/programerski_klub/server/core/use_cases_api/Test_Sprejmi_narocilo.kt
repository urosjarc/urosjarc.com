import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.test.KoinTest
import org.koin.test.inject
import si.programerski_klub.server.core.base.Entiteta
import si.programerski_klub.server.core.domain.placevanje.*
import si.programerski_klub.server.core.domain.uprava.Kontakt
import si.programerski_klub.server.core.extend.pripravi_DI
import si.programerski_klub.server.core.extend.resetiraj_DI
import si.programerski_klub.server.core.use_cases_api.Sprejmi_narocilo
import kotlin.test.assertEquals
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class `sprejmi narocilo` : KoinTest {

    val sprejmi_narocilo: Sprejmi_narocilo by this.inject()

    private fun novo_narocilo(): Narocilo {
        return Entiteta.random()
    }

    @AfterEach
    fun after_each() = this.resetiraj_DI()

    @Test
    fun `fail prejemnik je placnik`() {
        // - Pripravi narocilo
        val narocilo = this.novo_narocilo()

        // - Placnik naj bo enk prejemniku
        narocilo.placnik = narocilo.prejemnik

        // - Pripravi DI
        this.pripravi_DI()

        // - Preveri delovanje
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> {}
            else -> fail()
        }
    }

    @Test
    fun `fail preveliko narocilo`() {
        // - Pripravi narocilo.
        val narocilo = this.novo_narocilo()

        // - Pripravi premalo produktov na zalogo.
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 1)) }
        this.pripravi_DI(produkti = produkti_na_zalogi)

        // - Preveri delovanje.
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> {}
            else -> fail()
        }
    }

    @Test
    fun `fail produkt ne obstaja`() {
        // - Pripravi narocilo.
        val narocilo = this.novo_narocilo()

        // - Pripravi DI.
        this.pripravi_DI()

        // - Preveri delovanje.
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> {}
            else -> fail()
        }
    }

    @Test
    fun `fail prejemnik je zavrnjen`() {
        // - Pripravi narocilo
        val narocilo = this.novo_narocilo()

        // - Dodaj produkte na zalogo
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 10)) }
        this.pripravi_DI(produkti = produkti_na_zalogi)

        // - Ustvari pokvarjene kontakte
        val kontakti = mutableListOf<Kontakt>()
        narocilo.prejemnik.kontakti.forEach { kontakti.add(it.copy(data = "manjka")) }
        narocilo.prejemnik.kontakti = kontakti.toMutableSet()

        // - Preveri delovanje
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> TODO()
            Sprejmi_narocilo.Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> TODO()
            Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> TODO()
        }
    }

    @Test
    fun `fail placnik je zavrnjen`() {
        // - Pripravi narocilo.
        val narocilo = this.novo_narocilo()

        // - Dodaj produkte na zalogo.
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 10)) }
        this.pripravi_DI(produkti = produkti_na_zalogi)

        // - Ustvari placnika z napacnimi kontakti
        narocilo.placnik = narocilo.prejemnik.copy(ime = "xxx")
        narocilo.placnik?.kontakti?.forEach { it.data = "manjka" }

        // - Preveri delovanje.
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> TODO()
            Sprejmi_narocilo.Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> TODO()
            Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> TODO()
        }
    }

    @Test
    fun `fail narocilo se ni shranilo`() {
        // - Pripravi narocilo.
        val narocilo = this.novo_narocilo()

        // - Dodaj produkte na zalogo.
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 10)) }

        // - Pripravi DI.
//        this.pripravi_DI(produkti = produkti_na_zalogi, shrani_narocilo_return = mutableListOf(DbService.RezultatIdentifikacije.ERROR()))

        // - Preveri delovanje.
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> TODO()
            Sprejmi_narocilo.Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> TODO()
            Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> TODO()
        }
    }

    @Test
    fun `fail uporabnik ima ze narocnino`() {
        // - Pripravi narocilo.
        val narocilo = this.novo_narocilo()

        // - Dodaj produkte na zalogo.
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 10)) }

        // - Ustvari narocilo...
        val narocnina = Entiteta.random<Narocnina>()

        // - Pripravi DI.
        this.pripravi_DI(produkti = produkti_na_zalogi, narocnine = mutableListOf(narocnina))

        // - Preveri delovanje.
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> {}
            else -> fail()
        }
    }

    @Test
    fun `fail narocnina je zavrnjena`() {
        // - Pripravi narocilo.
        val narocilo = this.novo_narocilo()

        // - Dodaj produkte na zalogo.
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 10)) }

        // - Pripravi DI.
//        this.pripravi_DI(produkti = produkti_na_zalogi, shrani_narocnino_return = mutableListOf(DbService.RezultatIdentifikacije.ERROR()))

        // - Preveri delovanje.
        when (this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> TODO()
            Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> TODO()
            Sprejmi_narocilo.Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> TODO()
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> TODO()
            Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> TODO()
        }
    }

    @Test
    fun ok() {
        // - Pripravi narocilo
        val narocilo = this.novo_narocilo()

        // - Pripravi produkte na zalogi
        val produkti_na_zalogi = mutableListOf<Produkt>()
        narocilo.kosarica.forEach { produkti_na_zalogi.add(it.produkt.copy(id = it.produkt.id, zaloga = 10)) }
        this.pripravi_DI(produkti = produkti_na_zalogi)

        // - Preveri delovanje
        when (val r = this.sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> {
                assertEquals(expected = narocilo, actual = r.data)
            }

            else -> fail()
        }
    }
}
