package si.programerski_klub.server.core.base

import io.github.serpro69.kfaker.Faker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.uprava.Kontakt

abstract class UnikatnaEntiteta<T> : Entiteta<T> {
    @SerialName("_id")
    abstract val id: Id<T>

    @Serializable
    abstract class ZKontakti<T> : UnikatnaEntiteta<T>() {

        abstract var kontakti: MutableSet<Kontakt>

        fun ima_kontakt(kontakt_data: String): Boolean {
            for (kontakt in this.kontakti) {
                if (kontakt.data == kontakt_data && kontakt.nivo_validiranosti == Kontakt.NivoValidiranosti.POTRJEN) {
                    return true
                }
            }
            return false
        }

        fun dodaj_kontakte(kontakti: MutableSet<Kontakt>) {
            for (kontakt in kontakti) {
                var exists = false
                for (old_kontakt in this.kontakti) {
                    if (kontakt.enak(old_kontakt)) {
                        exists = true
                        if (kontakt.nivo_validiranosti > old_kontakt.nivo_validiranosti) {
                            old_kontakt.tip = kontakt.tip
                        }
                    }
                }
                if (!exists) {
                    this.kontakti.add(kontakt)
                }
            }
        }

        fun aktivni_kontakt(tip: Kontakt.Tip): Kontakt {
            val kontakti = mutableListOf<Kontakt>()
            for (kontakt in this.kontakti) {
                if (kontakt.tip == tip) kontakti.add(kontakt)
            }
            return kontakti.sortedBy { it.ustvarjen }[0]
        }

    }

}
