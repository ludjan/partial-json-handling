package json

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val city: String,
    val country: String,
    val coordinates: Coordinates
)