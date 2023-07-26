package domain

import base.AnyId
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
    var opis: String,
    val trajanje: Duration,
    val entiteta: String,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj()
) : Entiteta<Audit> {
    enum class Tip {
        STATUS_TIP_POSODOBITEV,
        TEST_DATUM_POSODOBITEV
    }
}
