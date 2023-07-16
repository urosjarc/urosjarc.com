package api.plugins

import api.extend.client_unauthorized
import api.extend.profil
import domain.Oseba
import extends.ime
import io.ktor.server.application.*
import io.ktor.server.auth.*
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.kotlin.logger

fun Oseba.profil(): Profil = Profil(id = this._id.toString(), tip = this.tip)

@Serializable
data class Profil(
    val id: String,
    val tip: Oseba.Tip,
) {
    companion object {
        val claim: String = ime<Profil>()
    }
}

val PreveriProfil = createRouteScopedPlugin(
    name = "PreveriProfil", createConfiguration = ::PluginConfiguration
) {
    val tipi: List<Oseba.Tip> = this.pluginConfig.tip_profila
    val log = this.logger()
    this.pluginConfig.apply {
        this@createRouteScopedPlugin.on(AuthenticationChecked) {
            val profil = it.profil()
            log.info("$tipi -> $profil")
            if (!this.tip_profila.contains(profil.tip)) {
                it.client_unauthorized()
            }
        }
    }
}

class PluginConfiguration {
    var tip_profila: List<Oseba.Tip> = listOf()
}
