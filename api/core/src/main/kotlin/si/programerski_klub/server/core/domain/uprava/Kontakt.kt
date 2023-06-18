package si.programerski_klub.server.core.domain.uprava

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.base.Entiteta

/**
 * Kontakt ne sme biti unique entiti zaradi tega ker se lahko zgodi da se bo otrok hotel vclaniti in v
 * tem primeru bo vnesel kontaktne podatke skrbnika. Zato se lahko kontakti podvojijo v podatkovni bazi.
 */
@Serializable
data class Kontakt(
    var data: String,
    var tip: Tip,
    var nivo_validiranosti: NivoValidiranosti = NivoValidiranosti.NI_VALIDIRAN,
    val ustvarjen: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
) : Entiteta<Kontakt> {
    enum class Tip { EMAIL, TELEFON }
    enum class NivoValidiranosti { NI_VALIDIRAN, VALIDIRAN, POTRJEN }

    override fun enak(entiteta: Kontakt): Boolean {
        return this.data == entiteta.data
    }
}
