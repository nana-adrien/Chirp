package empire.digiprem.com.core.domain.auth

data class AuthInfo (
    val accessToken:String,
    val refreshToken:String,
    val user:User
)