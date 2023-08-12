package data

import domain.Naslov
import kotlinx.serialization.Serializable

@Serializable
data class NaslovData(
    val naslov: Naslov
)
