package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id


data class Test(
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
    val id_oseba: Id<Oseba>,
): Entiteta<Test>()

data class Status(
    val tip: Tip,
    val id_naloga: Id<Naloga>,
    val id_test: Id<Test>
) : Entiteta<Status>() {
    enum class Tip { USPEH, NEUSPEH }
}
