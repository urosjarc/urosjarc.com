package api.response

import base.Encrypted
import kotlinx.serialization.Serializable

@Serializable
data class ErrorRes(
    val napaka: Tip,
    val status: Encrypted,
    val info: Encrypted
) {
    enum class Tip {
        UPORABNISKA,
        SISTEMSKA
    }
}
