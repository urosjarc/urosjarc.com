package api.models

import kotlinx.serialization.Serializable
import extends.ime
import domain.Oseba

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
