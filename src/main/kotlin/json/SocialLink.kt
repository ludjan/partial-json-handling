package json

import kotlinx.serialization.Serializable

@Serializable
data class SocialLink(
    val platform: String,
    val url: String
)