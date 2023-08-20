package api.plugins

import api.extend.client_unauthorized
import api.extend.profil
import base.Id
import domain.Oseba
import extend.ime
import io.ktor.server.application.*
import io.ktor.server.auth.*
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.kotlin.logger

fun Oseba.profil(): Profil = Profil(oseba_id = this._id, tip = this.tip)

@Serializable
data class Profil(
    val oseba_id: Id<Oseba>,
    val tip: Set<Oseba.Tip>,
) {
    companion object {
        val claim: String = ime<Profil>()
    }
}

val PreveriProfil = createRouteScopedPlugin(
    name = "PreveriProfil", createConfiguration = ::PluginConfiguration
) {
    val tipi: Set<Oseba.Tip> = this.pluginConfig.tip_profila
    val log = this.logger()
    this.pluginConfig.apply {
        this@createRouteScopedPlugin.on(AuthenticationChecked) {
            val profil = it.profil()
            log.info("$tipi -> $profil")
            if (this.tip_profila.intersect(profil.tip).isEmpty()) {
                it.client_unauthorized()
            }
        }
    }
}

class PluginConfiguration {
    var tip_profila: Set<Oseba.Tip> = setOf()
}
