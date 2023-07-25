package use_cases

import base.AnyId
import base.Id
import domain.*
import extend.danes
import extend.dodaj
import extend.nakljucni
import extend.zdaj
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import services.DbService
import services.counters
import services.faker
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class Ustvari_testne_podatke(
    private val db: DbService,
) {

    private inline fun <reified T : Any> nakljucni(): T {
        val obj = faker.randomProvider.randomClassInstance<T> {
            this.typeGenerator<Id<T>> { Id() }
            this.typeGenerator<AnyId> { AnyId() }
            this.typeGenerator<MutableSet<T>> { mutableSetOf() }
            this.typeGenerator<Duration> { Random.nextInt(2, 10).minutes }
            this.typeGenerator<LocalDate> { LocalDate.danes(dDni = Random.nextInt(3, 20)) }
            this.typeGenerator<LocalDateTime> { LocalDateTime.zdaj() }
            this.typeGenerator<String> { pInfo ->
                val value = counters.getOrDefault(pInfo.name, -1) + 1
                counters[pInfo.name] = value
                "${pInfo.name}_${value}"
            }
        }
        return obj
    }

    fun zdaj(vse: Boolean = false) {
        db.sprazni()

        /**
         * Ustvari nujne stvari
         */
        val admin = Oseba(
            ime = "Uroš",
            priimek = "Jarc",
            username = "urosjarc",
            tip = mutableSetOf(Oseba.Tip.SERVER, Oseba.Tip.ADMIN)
        )
        val admin_telefon = Kontakt(
            oseba_id = mutableSetOf(admin._id),
            data = "Uros Jarc",
            tip = Kontakt.Tip.TELEFON
        )
        val admin_email = Kontakt(
            oseba_id = mutableSetOf(admin._id),
            data = "info@urosjarc.com",
            tip = Kontakt.Tip.EMAIL
        )
        db.ustvari(admin)
        db.ustvari(admin_telefon)
        db.ustvari(admin_email)
        if (!vse) return

        /**
         * Ustvari osebe
         */
        val osebe = this.ustvari_osebe(n = 3, tip = Oseba.Tip.UCITELJ)
        osebe += this.ustvari_osebe(n = 10, tip = Oseba.Tip.UCENEC)
        osebe += this.ustvari_osebe(n = 10, tip = Oseba.Tip.KONTAKT)
        val ucenci = osebe.filter { it.tip.contains(Oseba.Tip.UCENEC) }.toMutableSet()
        val ucitelji = osebe.filter { it.tip.contains(Oseba.Tip.UCITELJ) }.toMutableSet()


        /**
         * Ustvari naslove
         */
        val naslovi = mutableSetOf<Naslov>()
        osebe.forEach {
            naslovi += this.ustvari_naslov(n = 2, it)
        }

        /**
         * Ustvari ucenje
         */
        val ucenje = mutableSetOf<Ucenje>()
        (0 until 10).forEach {
            ucenci.forEach { ucenje += this.ustvari_ucenje(ucitelj = ucitelji.random(), ucenec = it) }
            ucitelji.forEach { ucenje += this.ustvari_ucenje(ucitelj = it, ucenec = ucenci.random()) }
        }

        /**
         * Ustvari kontakte
         */
        val kontakti = mutableSetOf<Kontakt>()
        osebe.forEach {
            kontakti += this.ustvari_kontakte(n = 2, tip = Kontakt.Tip.EMAIL, osebe = osebe.nakljucni(2).dodaj(it))
            kontakti += this.ustvari_kontakte(n = 2, tip = Kontakt.Tip.TELEFON, osebe = osebe.nakljucni(2).dodaj(it))
        }

        /**
         * Ustvari sporocila
         */
        val sporocila = mutableSetOf<Sporocilo>()
        kontakti.forEach {
            val prejemniki = kontakti.nakljucni(n = 3)
            sporocila += this.ustvari_sporocila(n = 10, posiljatelj = it, prejemniki = prejemniki)
        }

        /**
         * Ustvari zvezek
         */
        val zvezki = this.ustvari_zvezek(n = 10)

        /**
         * Ustvari tematike
         */
        val tematike = mutableSetOf<Tematika>()
        zvezki.forEach { tematike += this.ustvari_tematika(n = 10, zvezek = it) }

        /**
         * Ustvari naloge
         */
        val naloge = mutableSetOf<Naloga>()
        tematike.forEach { naloge += this.ustvari_naloge(n = 20, tematika = it) }

        /**
         * Ustvari teste in statuse
         */
        val testi = this.ustvari_teste(n = 3, ucenci = ucenci, admini = ucitelji, naloge = naloge.nakljucni(n = 30))

        /**
         * Ustvari statuse
         */
        val statusi = mutableSetOf<Status>()
        ucenci.forEach { ucenec ->
            testi.forEach { test ->
                naloge.nakljucni(10).forEach { naloga ->
                    val status = this.ustvari_status(naloga = naloga, test = test, oseba = ucenec)
                    statusi += status
                }
            }
        }

        /**
         * Ustvari audite
         */
        statusi.forEach {
            this.ustvari_audit(n = 3, entitete_id = mutableSetOf(it.oseba_id, it.naloga_id, it.test_id))
        }

        db.ustvari(osebe)
        db.ustvari(naslovi)
        db.ustvari(ucenje)
        db.ustvari(kontakti)
        db.ustvari(sporocila)
        db.ustvari(testi)
        db.ustvari(statusi)
        db.ustvari(naloge)
        db.ustvari(tematike)
        db.ustvari(zvezki)
    }

    private

    fun ustvari_osebe(n: Int, tip: Oseba.Tip): MutableSet<Oseba> {
        return (0..n).map {
            val oseba = this.nakljucni<Oseba>().apply {
                this.tip = mutableSetOf(tip)
            }
            oseba
        }.toMutableSet()
    }

    fun ustvari_naslov(n: Int, oseba: Oseba): MutableSet<Naslov> {
        return (0..n).map {
            val naslov = this.nakljucni<Naslov>().apply {
                this.oseba_id = oseba._id
            }
            naslov
        }.toMutableSet()
    }

    fun ustvari_ucenje(ucitelj: Oseba, ucenec: Oseba): Ucenje {
        return this.nakljucni<Ucenje>().apply {
            this.oseba_ucenec_id = ucenec._id
            this.oseba_ucitelj_id = ucitelj._id
        }
    }

    fun ustvari_kontakte(n: Int, tip: Kontakt.Tip, osebe: MutableSet<Oseba>): MutableSet<Kontakt> {
        return (0..n).map {
            val kontakt = this.nakljucni<Kontakt>().apply {
                this.oseba_id = osebe.map { it._id }.toMutableSet()
                this.tip = tip
            }
            kontakt
        }.toMutableSet()
    }

    fun ustvari_sporocila(n: Int, posiljatelj: Kontakt, prejemniki: MutableSet<Kontakt>): MutableSet<Sporocilo> {
        return (0..n).map {
            val sporocilo = this.nakljucni<Sporocilo>().apply {
                this.kontakt_posiljatelj_id = posiljatelj._id
                this.kontakt_prejemnik_id = prejemniki.map { it._id }.toMutableSet()
            }
            sporocilo
        }.toMutableSet()
    }

    fun ustvari_zvezek(n: Int): MutableSet<Zvezek> {
        return (0..n).map {
            val zvezek = this.nakljucni<Zvezek>()
            zvezek
        }.toMutableSet()
    }

    fun ustvari_tematika(n: Int, zvezek: Zvezek): MutableSet<Tematika> {
        return (0..n).map {
            val tematika = this.nakljucni<Tematika>().apply {
                this.zvezek_id = zvezek._id
            }
            tematika
        }.toMutableSet()
    }

    fun ustvari_naloge(n: Int, tematika: Tematika): MutableSet<Naloga> {
        return (0..n).map {
            val naloga = this.nakljucni<Naloga>().apply {
                this.tematika_id = tematika._id
            }
            naloga
        }.toMutableSet()
    }

    fun ustvari_teste(
        n: Int,
        admini: MutableSet<Oseba>,
        ucenci: MutableSet<Oseba>,
        naloge: MutableSet<Naloga>
    ): MutableSet<Test> {
        return (0..n).map {
            val test = this.nakljucni<Test>().apply {
                this.oseba_admin_id = admini.map { it._id }.toMutableSet()
                this.oseba_ucenec_id = ucenci.map { it._id }.toMutableSet()
                this.naloga_id = naloge.map { it._id }.toMutableSet()
            }
            test
        }.toMutableSet()
    }

    fun ustvari_status(naloga: Naloga, test: Test, oseba: Oseba): Status {
        val status = this.nakljucni<Status>().apply {
            this.naloga_id = naloga._id
            this.test_id = test._id
            this.oseba_id = oseba._id
        }
        return status
    }

    fun ustvari_audit(n: Int, entitete_id: MutableSet<Id<*>>): MutableSet<Audit> {
        return (0..n).map {
            val audit = this.nakljucni<Audit>().apply {
                this.entitete_id = entitete_id.map { it.vAnyId() }.toMutableSet()
            }
            audit
        }.toMutableSet()
    }

    fun ustvari_napaka(n: Int, entitete: MutableSet<Entiteta<Any>>): MutableSet<Napaka> {
        return (0..n).map {
            val napaka = this.nakljucni<Napaka>().apply {
                this.entitete_id = entitete.map { it._id.vAnyId() }.toMutableSet()
            }
            napaka
        }.toMutableSet()
    }
}
