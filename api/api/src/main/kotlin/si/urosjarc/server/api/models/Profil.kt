package si.urosjarc.server.api.models

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Oseba

@Serializable
data class Profil(
    val id: String,
    val tip: Oseba.Tip,
) {
    companion object {
        val claim: String = name<Profil>()
    }
}

fun Oseba.profil(): Profil = Profil(id = this.id.value, tip = this.tip)
