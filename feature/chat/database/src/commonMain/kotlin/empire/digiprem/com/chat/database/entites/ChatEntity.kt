package empire.digiprem.com.chat.database.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @PrimaryKey
    val chatId:String,
    val lastActivityAt:Long
)