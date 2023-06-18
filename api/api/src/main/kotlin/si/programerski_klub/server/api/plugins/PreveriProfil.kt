package si.programerski_klub.server.api.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.api.extend.client_unauthorized
import si.programerski_klub.server.api.extend.profil
import si.programerski_klub.server.core.domain.uprava.Oseba

val PreveriProfil = createRouteScopedPlugin(
    name = "PreveriProfil",
    createConfiguration = ::PluginConfiguration
) {
    val oseba: Oseba.Tip = this.pluginConfig.tip_profila
    val log = this.logger()
    this.pluginConfig.apply {
        this@createRouteScopedPlugin.on(AuthenticationChecked) {
            val profil = it.profil()
            log.info("$oseba -> $profil")
            if (!profil.tip.contains(this.tip_profila)) {
                it.client_unauthorized()
            }
        }
    }
}

class PluginConfiguration {
    var tip_profila: Oseba.Tip = Oseba.Tip.CLAN
}
