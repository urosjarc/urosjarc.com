package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class Test(
    @Contextual override var _id: String? = null,
    @Contextual var oseba_id: String? = null,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta

@Serializable
data class Status(
    @Contextual override var _id: String? = null,
    @Contextual var naloga_id: String? = null,
    @Contextual var test_id: String? = null,
    val tip: Tip,
    val pojasnilo: String
) : Entiteta {
    enum class Tip { USPEH, NEUSPEH }
}
