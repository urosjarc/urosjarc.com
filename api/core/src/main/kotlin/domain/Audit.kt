package domain

import base.AnyId
import base.Encrypted
import base.Id
import extend.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Audit(
    override var _id: Id<Audit> = Id(),
    var entitete_id: Set<AnyId>,
    val tip: Tip,
    val trajanje: Duration,
    @Encrypted val ustvarjeno: LocalDateTime = LocalDateTime.zdaj(),
    @Encrypted var opis: String,
    @Encrypted val entiteta: String,
) : Entiteta<Audit> {
    enum class Tip {
        STATUS_TIP_POSODOBITEV,
        TEST_DATUM_POSODOBITEV
    }
}
