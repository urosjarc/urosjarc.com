package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id


@Serializable
data class Test(
    @Contextual
    override val id: Id<Test> = Id(),
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
    @Contextual
    val id_oseba: Id<Oseba>,
) : Entiteta<Test>()

@Serializable
data class Status(
    @Contextual
    override val id: Id<Status> = Id(),
    val tip: Tip,
    @Contextual
    val id_naloga: Id<Naloga>,
    @Contextual
    val id_test: Id<Test>,
    val pojasnilo: String
) : Entiteta<Status>() {
    enum class Tip { USPEH, NEUSPEH }
}
