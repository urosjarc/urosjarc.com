package si.urosjarc.server.core.domain

import kotlinx.serialization.Serializable


@Serializable
sealed interface Entiteta {
    var _id: String?
}
