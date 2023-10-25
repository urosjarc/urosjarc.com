package gui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Vektor(
    val x: Int,
    val y: Int,
) {
    companion object {
        val NIC get() = Vektor(x=0,y=0)
    }
}
