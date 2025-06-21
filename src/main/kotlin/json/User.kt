package json

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val profile: Profile,
    val posts: List<Post>
)