package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Id


@Serializable
data class Test(
    @SerialName("_id")
    @Contextual override var id: Id<Test> = Id(),
    @Contextual val oseba_id: Id<Oseba>,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta<Test>()

@Serializable
data class Status(
    @SerialName("_id")
    @Contextual override var id: Id<Status> = Id(),
    @Contextual val naloga_id: Id<Naloga>,
    @Contextual val test_id: Id<Test>,
    val tip: Tip,
    val pojasnilo: String
) : Entiteta<Status>() {
    enum class Tip { USPEH, NEUSPEH }
}
