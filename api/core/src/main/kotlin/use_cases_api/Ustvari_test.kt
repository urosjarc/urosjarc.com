package use_cases_api

import base.Encrypted
import base.Id
import domain.Naloga
import domain.Oseba
import domain.Test
import kotlinx.datetime.LocalDate
import services.DbService
import services.EmailService
import services.TelefonService

class Ustvari_test(
    private val telefon: TelefonService,
    private val email: EmailService,
    private val db: DbService
) {

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
        val db_ucenci = this.db.dobi(test.oseba_ucenec_id)
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

        return when (this.db.ustvari(test)) {
            true -> Rezultat.PASS(test = test)
            false -> Rezultat.ERROR_TEST_SE_NI_SHRANIL
        }

    }
}
