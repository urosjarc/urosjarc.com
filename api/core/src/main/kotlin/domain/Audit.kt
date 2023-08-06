package domain

import base.AnyId
import base.Encrypted
import base.Id
import extend.zdaj
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
    @Contextual var opis: Encrypted,
    @Contextual val entiteta: Encrypted,
) : Entiteta<Audit> {
    enum class Tip {
        STATUS_TIP_POSODOBITEV,
        TEST_DATUM_POSODOBITEV
    }
}
