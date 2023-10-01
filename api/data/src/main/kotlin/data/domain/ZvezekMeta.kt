package data.domain

import kotlinx.serialization.Serializable

@Serializable
data class ZvezekMeta(
    val slika: Int,
    val tematika: String,
)
