package si.urosjarc.server.admin

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import si.urosjarc.server.app.base.App
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.domain.napredovanje.Naloga
import si.urosjarc.server.core.domain.napredovanje.Postaja
import si.urosjarc.server.core.domain.napredovanje.Program
import si.urosjarc.server.core.domain.napredovanje.Test
import si.urosjarc.server.core.domain.placevanje.Narocilo
import si.urosjarc.server.core.domain.placevanje.Narocnina
import si.urosjarc.server.core.domain.placevanje.Ponudba
import si.urosjarc.server.core.domain.placevanje.Produkt
import si.urosjarc.server.core.domain.uprava.Oseba
import si.urosjarc.server.core.extends.now
import si.urosjarc.server.core.repos.DbPostRezultat
import si.urosjarc.server.core.services.DbService
import kotlin.random.Random

class Seeder : KoinComponent {

    val db: DbService by this.inject()

    private fun produkti(n: Int): Set<Produkt> {
        val ids = mutableSetOf<Produkt>()
        for (i in 0..n) {
            val produkt = Entiteta.random<Produkt>()
            ids.add(produkt)
            when (val r = this.db.produkti.shrani(produkt)) {
                is DbPostRezultat.DATA -> r.data
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
            }
        }
        return ids
    }

    fun ponudbe(n: Int): List<Ponudba> {
        val objs = mutableListOf<Ponudba>()
        for (i in 0..n) {
            val ponudba = Entiteta.random<Ponudba>()
            ponudba.produkti = this.produkti(n = 1).map { produkt -> produkt.id }.toMutableSet()
            when (val r = this.db.ponudbe.shrani(ponudba)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }
        }

        return objs
    }

    fun naloge(n: Int): List<Naloga> {
        val objs = mutableListOf<Naloga>()
        for (i in 0..n) {
            val naloga = Entiteta.random<Naloga>()
            when (val r = this.db.naloge.shrani(naloga)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }
        }

        return objs
    }

    fun testi(n: Int): List<Test> {
        val objs = mutableListOf<Test>()
        for (i in 0..n) {
            val test = Entiteta.random<Test>()
            test.naloge = this.naloge(2).toMutableSet()
            when (val r = this.db.testi.shrani(test)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }
        }

        return objs
    }


    fun osebe(n: Int): List<Oseba> {
        val objs = mutableListOf<Oseba>()
        for (i in 0..n) {
            val oseba = Entiteta.random<Oseba>()
            oseba.tip.addAll(Oseba.Tip.values())
            when (val r = this.db.osebe.shrani_ali_posodobi(oseba)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }

            for (j in 0..3) {
                val narocilo = this.narocila(1, oseba = oseba).first()
                this.narocnine(1, narocilo = narocilo, uporabnik = oseba, placnik = oseba)
                this.programi(3, uporabnik = oseba)
            }
        }

        return objs
    }

    @Throws(Exception::class)
    private fun postaje(n: Int): Postaja {
        val cakalnica = mutableListOf<Postaja>()

        // Ustvari zacetnega
        val zacetek = Entiteta.random<Postaja>()
        zacetek.stars = null
        when (val r = this.db.postaje.shrani(zacetek)) {
            is DbPostRezultat.DATA -> cakalnica.add(r.data)
            is DbPostRezultat.FATAL_DB_NAPAKA -> throw Exception()
        }

        // Povezi v strukturo
        for (i in 0..n) {
            val trenutni = cakalnica[i]
            for (i in 0..Random.nextInt(1, 4)) {
                val novi = Entiteta.random<Postaja>()

                if(Random.nextInt(10) < 3){
                    novi.test = this.testi(1).first().id
                    novi.vaje = this.testi(1).first().id
                }

                when (val r = this.db.postaje.shrani(novi)) {
                    is DbPostRezultat.DATA -> {
                        trenutni.povezi(r.data)
                        cakalnica.add(r.data)
                    }

                    is DbPostRezultat.FATAL_DB_NAPAKA -> throw Exception()
                }
            }
        }

        // Posodobi strukturo
        for (postaja in cakalnica) {
            when (this.db.postaje.shrani(postaja)) {
                is DbPostRezultat.DATA -> {}
                is DbPostRezultat.FATAL_DB_NAPAKA -> throw Exception()
            }
        }

        return zacetek
    }

    private fun programi(n: Int, uporabnik: Oseba): List<Program> {
        val objs = mutableListOf<Program>()
        for (i in 0..n) {
            val zacetek = this.postaje(4)
            val program = Program(
                produkt = this.produkti(0).first().id,
                zacetek = zacetek.id,
                checkpoint = zacetek.id,
                uporabnik = uporabnik.id
            )
            when (val r = this.db.programi.shrani(program)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }
        }

        return objs
    }

    private fun narocila(n: Int, oseba: Oseba): List<Narocilo> {
        val objs = mutableListOf<Narocilo>()
        for (i in 0..n) {
            val narocilo = Narocilo(
                prejemnik = oseba,
                placnik = oseba,
                kosarica = setOf(Entiteta.random())
            )
            when (val r = this.db.narocila.shrani(narocilo)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }
        }

        return objs
    }

    private fun narocnine(n: Int, narocilo: Narocilo, uporabnik: Oseba, placnik: Oseba): List<Narocnina> {
        val objs = mutableListOf<Narocnina>()
        for (i in 0..n) {

            val produkt = this.produkti(1).first()

            val narocnina = Narocnina(
                produkt = produkt,
                id_narocila = narocilo.id,
                id_placnika = placnik.id,
                id_uporabnika = uporabnik.id,
                frekvenca_racunov = DatePeriod(months = 1),
                zacetki = setOf(LocalDateTime.now()),
                konci = setOf(LocalDateTime.now()),
                status = Narocnina.Status.AKTIVNA,
            )

            when (val r = this.db.narocnine.shrani(narocnina)) {
                is DbPostRezultat.FATAL_DB_NAPAKA -> {}
                is DbPostRezultat.DATA -> objs.add(r.data)
            }
        }

        return objs
    }
}

fun main() {
    App.pripravi_DI(tip = App.Tip.TEST)
    val seeder = Seeder()

    seeder.db.drop()
    seeder.ponudbe(n = 10)
    seeder.osebe(n = 10)
}
