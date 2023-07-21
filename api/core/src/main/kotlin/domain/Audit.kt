package domain

import base.AnyId
import base.Id
import extends.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Audit(
    override var _id: Id<Audit> = Id(),
    var entitete_id: List<AnyId> = listOf(),
    val tip: Tip,
    val opis: String,
    val trajanje: Duration,
    val entiteta: String,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj()
) : Entiteta<Audit> {
    enum class Tip {
        STATUS_TIP_POSODOBITEV,
        TEST_DATUM_POSODOBITEV
    }
}
