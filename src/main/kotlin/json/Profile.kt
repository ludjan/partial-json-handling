package json

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val fullName: String,
    val picture: String,
    val bio: String,
    val location: Location,
    val socialLinks: List<SocialLink>
)