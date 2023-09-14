package api.response

import core.base.Encrypted
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ErrorRes(
    val napaka: Tip,
    @Contextual val status: Encrypted,
    @Contextual val info: Encrypted
) {
    enum class Tip {
        UPORABNISKA,
        SISTEMSKA
    }
}
