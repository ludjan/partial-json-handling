package json

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val tags: List<String>,
    val published: Boolean,
    val likes: Int
)