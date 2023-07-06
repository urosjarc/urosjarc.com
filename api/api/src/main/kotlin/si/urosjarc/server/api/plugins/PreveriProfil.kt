package si.urosjarc.server.api.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.apache.logging.log4j.kotlin.logger
import si.urosjarc.server.api.extend.client_unauthorized
import si.urosjarc.server.api.extend.profil
import si.urosjarc.server.core.domain.Oseba

val PreveriProfil = createRouteScopedPlugin(
    name = "PreveriProfil",
    createConfiguration = ::PluginConfiguration
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
