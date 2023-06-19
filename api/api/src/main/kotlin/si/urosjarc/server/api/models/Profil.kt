package si.urosjarc.server.api.models

import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.toId
import si.urosjarc.server.core.domain.uprava.Oseba

@Serializable
data class Profil(
    val _id: String,
    val tip: Set<Oseba.Tip>,
) {
    val id: Id<Oseba>
        get() = this._id.toId()

    companion object {
        val claim: String = Profil::class.simpleName.toString()
    }
}

fun Oseba.profil(): Profil = Profil(_id = this.id.toString(), tip = this.tip)
