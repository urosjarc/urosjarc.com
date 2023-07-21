package api.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorRes(
    val napaka: Tip,
    val status: String,
    val info: String
) {
    enum class Tip {
        UPORABNISKA,
        SISTEMSKA
    }
}
