package empire.digiprem.com.core.data.dto


import kotlinx.serialization.Serializable

@Serializable
data class UserSerializable(
    val id:String,
    val email:String,
    val username:String,
    val hasVerifiedEmail:Boolean,
    val profilePicture:String,
)