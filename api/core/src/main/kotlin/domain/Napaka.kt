package domain

import extends.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.kotlin.logger

@Serializable
data class Napaka(
    override var _id: String? = null,
    var entitete_id: List<String> = listOf(),
    val tip: Tip,
    val vsebina: String,
    val dodatno: String,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj()
) : Entiteta {
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
