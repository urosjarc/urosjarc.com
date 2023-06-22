package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id


data class Test(
    override val id: Id<Test> = Id(),
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
    val id_oseba: Id<Oseba>,
) : Entiteta<Test>(id = id)

data class Status(
    override val id: Id<Status> = Id(),
    val tip: Tip,
    val id_naloga: Id<Naloga>,
    val id_test: Id<Test>,
    val pojasnilo: String
) : Entiteta<Status>(id = id) {
    enum class Tip { USPEH, NEUSPEH }
}
