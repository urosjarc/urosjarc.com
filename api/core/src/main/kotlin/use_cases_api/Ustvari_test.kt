package use_cases_api

import base.Encrypted
import base.Id
import domain.*
import extend.encrypted
import kotlinx.datetime.LocalDate
import org.apache.logging.log4j.kotlin.logger
import services.DbService
import services.EmailService
import use_cases.Ustvari_templejt

class Ustvari_test(
    private val ustvari_template: Ustvari_templejt,
    private val email: EmailService,
    private val db: DbService
) {

    val log = this.logger()

    sealed interface Rezultat {
        data class PASS(val test: Test) : Rezultat
        data class WARN_MANJKAJOCI_ELEMENTI(
            val ucenci: Int,
            val admini: Int,
            val naloge: Int
        ) : Rezultat

        object WARN_AVTOR_NE_OBSTAJA : Rezultat

        object ERROR_TEST_SE_NI_SHRANIL : Rezultat

    }

    fun zdaj(
        avtor: Id<Oseba>,
        naslov: Encrypted, podnaslov: Encrypted, deadline: LocalDate,
        admini: MutableCollection<Id<Oseba>>, ucenci: Collection<Id<Oseba>>, naloge: Collection<Id<Naloga>>
    ): Rezultat {

        admini.add(avtor)

        val test = Test(
            naloga_id = naloge.toMutableSet(),
            oseba_admin_id = admini.toMutableSet(),
            oseba_ucenec_id = ucenci.toMutableSet(),
            deadline = deadline,
            naslov = naslov,
            podnaslov = podnaslov,
        )

        //Izbrani elementi ki so bili najdeni...
        val db_admini = this.db.dobi(test.oseba_admin_id)
        val db_ucenci: List<Oseba> = this.db.dobi(test.oseba_ucenec_id)
        val db_naloge = this.db.dobi(test.naloga_id)

        //Elementi ki manjkajo iz originalnih spiskov...
        val manjkajoci_admini = admini.minus(db_admini.map { it._id }.toSet())
        val manjkajoci_ucenci = ucenci.minus(db_ucenci.map { it._id }.toSet())
        val manjkajoci_naloge = naloge.minus(db_naloge.map { it._id }.toSet())

        //Ce kateri koli element manjka potem vrni opozorilo...
        if (manjkajoci_admini.isNotEmpty() || manjkajoci_ucenci.isNotEmpty() || manjkajoci_naloge.isNotEmpty()) {
            return Rezultat.WARN_MANJKAJOCI_ELEMENTI(
                ucenci = db_ucenci.filter { manjkajoci_ucenci.contains(it._id) }.size,
                admini = db_admini.filter { manjkajoci_admini.contains(it._id) }.size,
                naloge = db_naloge.filter { manjkajoci_naloge.contains(it._id) }.size,
            )
        }

        if (!this.db.ustvari(test)) {
            return Rezultat.ERROR_TEST_SE_NI_SHRANIL
        }

        val kontakti = this.db.vsebuje<Kontakt, Oseba>(Kontakt::oseba_id, test.oseba_ucenec_id)
        val serverji = db.oseba_najdi(tip = Oseba.Tip.SERVER)
        val sporocila = mutableListOf<Sporocilo>()

        next_kontakt@ for (kontakt in kontakti.filter { it.tip == Kontakt.Tip.EMAIL }) {
            val oseba = db_ucenci.filter { kontakt.oseba_id.contains(it._id) }.first()

            for (serverData in serverji) {
                for (serverKontaktData in serverData.kontakt_refs.filter { it.kontakt.tip == Kontakt.Tip.EMAIL }) {

                    val template = ustvari_template.email_obvestilo_prejema_testa(prejemnik = oseba, test = test)

                    if (this.email.poslji_email(
                            fromName = template.posiljatelj,
                            from = serverKontaktData.kontakt.data.decrypt(),
                            to = kontakt.data.decrypt(),
                            subject = template.subjekt,
                            html = template.html
                        )
                    ) {
                        val sporocilo = Sporocilo(
                            kontakt_posiljatelj_id = serverKontaktData.kontakt._id,
                            kontakt_prejemnik_id = mutableSetOf(kontakt._id),
                            vsebina = template.html.encrypted()
                        )
                        db.ustvari(sporocilo)
                        sporocila.add(sporocilo)
                        continue@next_kontakt
                    }

                }
            }
        }

        this.log.info("Ustvarjenih je bilo: ${sporocila.size}")

        return Rezultat.PASS(test = test)

    }
}
