package empire.digiprem.com.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val user: UserSerializable
)
