package empire.digiprem.com.chat.database.entites

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithSender (
    @Embedded
    val message:ChatMessageEntity,
    @Relation(
        parentColumn = "senderId",
        entityColumn = "userId"
    )
    val sender:ChatParticipantEntity
)