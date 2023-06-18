package si.programerski_klub.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger
import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.repos.DbRezultatId
import si.programerski_klub.server.core.services.DbService

class Preveri_zalogo(
    val db: DbService
) {
    val log = this.logger()

    sealed interface Rezultat {
        object PASS : Rezultat
        data class ERROR_PRODUKT_NE_OBSTAJA(val id: Id<Produkt>) : Rezultat
        object ERROR_PREMAJHNA_ZALOGA : Rezultat
    }

    fun zdaj(narocilo: Narocilo): Rezultat {
        val racun = mutableMapOf<Id<Produkt>, Int>()
        narocilo.kosarica.forEach {
            racun[it.produkt.id] = racun.getOrDefault(it.produkt.id, 0) + it.kolicina
        }
        for ((id_produkta, povprasevanje) in racun) {
            when (val r = this.db.produkti.en(id = id_produkta)) {
                is DbRezultatId.ERROR -> return Rezultat.ERROR_PRODUKT_NE_OBSTAJA(id_produkta)
                is DbRezultatId.DATA -> when (r.data.zaloga >= povprasevanje) {
                    true -> {}
                    false -> return Rezultat.ERROR_PREMAJHNA_ZALOGA
                }
            }
        }

        return Rezultat.PASS
    }

}
