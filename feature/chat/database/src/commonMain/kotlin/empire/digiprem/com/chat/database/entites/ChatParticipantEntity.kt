package empire.digiprem.com.chat.database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatParticipantEntity(
    @PrimaryKey
    val userID:String,
    val username:String,
    val profilePictureUrl:String?
)
