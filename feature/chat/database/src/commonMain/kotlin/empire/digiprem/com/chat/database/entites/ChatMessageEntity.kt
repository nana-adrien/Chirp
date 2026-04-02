package empire.digiprem.com.chat.database.entites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["chatId"],
            childColumns = ["chatId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChatMessageEntity(
    @PrimaryKey
    val messageId:String,
    val chatId:String,
    val senderId:String,
    val content:String,
    val timestamp:Long,
    val deliveryStatus:String,
    val deliveryStatusTimestamp:Long=timestamp
)