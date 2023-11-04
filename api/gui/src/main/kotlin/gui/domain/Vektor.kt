package gui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Vektor(
    var x: Int,
    var y: Int,
) {
    companion object {
        val NIC get() = Vektor(x=0,y=0)
    }
}
