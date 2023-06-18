package si.programerski_klub.server.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorRes(
    val napaka: Tip,
    val rezultat: String,
    val info: String?
) {
    enum class Tip {
        UPORABNISKA,
        SISTEMSKA
    }
}
