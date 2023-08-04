package domain

import base.AnyId
import base.Encrypted
import base.Id
import extend.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.kotlin.logger

@Serializable
data class Napaka(
    override var _id: Id<Napaka> = Id(),
    var entitete_id: Set<AnyId>,
    val tip: Tip,
    @Encrypted val ustvarjeno: LocalDateTime = LocalDateTime.zdaj(),
    @Encrypted val vsebina: String,
    @Encrypted val dodatno: String,
) : Entiteta<Napaka> {
    enum class Tip { ERROR, WARN, FATAL }

    fun logiraj() {
        val log = this.logger()
        when (this.tip) {
            Tip.ERROR -> log.error(this)
            Tip.WARN -> log.warn(this)
            Tip.FATAL -> log.fatal(this)
        }
    }

}
