package json

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User
)