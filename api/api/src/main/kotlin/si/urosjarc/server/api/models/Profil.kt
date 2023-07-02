package si.urosjarc.server.api.models

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.extend.ime

@Serializable
data class Profil(
    val id: String,
    val tip: Oseba.Tip,
) {
    companion object {
        val claim: String = ime<Profil>()
    }
}

fun Oseba.profil(): Profil = Profil(id = this._id.toString(), tip = this.tip)
