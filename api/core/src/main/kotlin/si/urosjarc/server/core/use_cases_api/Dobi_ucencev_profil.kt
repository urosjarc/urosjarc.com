package si.urosjarc.server.core.use_cases_api

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Status
import si.urosjarc.server.core.domain.Test
import si.urosjarc.server.core.services.DbService

class Dobi_ucencev_profil(
    val dbService: DbService
) {
    @Serializable
    data class Data(
        val test: Test,
        val napredek: Float
    )

    fun zdaj(id: Id<Oseba>): List<Data> {

        val domenskiGraf = dbService.izvedi {
            dbService.statusRepo.dobi_statuse(id_osebe = id)
        }

        val podatki = mutableListOf<Data>()
        domenskiGraf.test.values.forEach { test ->
            val vsi_statusi = domenskiGraf.vsi_otroci(test) { it.status }
            val uspeh_statusi = vsi_statusi.filter { it.tip == Status.Tip.USPEH }
            val podatek = Data(test = test, napredek = (uspeh_statusi.size.toFloat() / vsi_statusi.size) * 100f)
            podatki.add(podatek)
        }

        return podatki
    }
}
