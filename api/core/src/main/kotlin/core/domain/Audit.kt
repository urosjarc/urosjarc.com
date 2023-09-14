package core.domain

import core.base.AnyId
import core.base.Id
import core.extend.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Audit(
    override var _id: Id<Audit> = Id(),
    var entitete_id: Set<AnyId>,
    val tip: Tip,
    val trajanje: Duration,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj(),
    @Contextual var opis: core.base.Encrypted,
    @Contextual val entiteta: core.base.Encrypted,
) : Entiteta<Audit> {
    enum class Tip {
        STATUS_TIP_POSODOBITEV, TEST_DATUM_POSODOBITEV
    }
}
