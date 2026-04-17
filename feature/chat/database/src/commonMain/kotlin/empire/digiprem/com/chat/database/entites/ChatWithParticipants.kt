package empire.digiprem.com.chat.database.entites

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import empire.digiprem.com.chat.database.view.LastMessageView

data class ChatWithParticipants(
    @Embedded
    val chat: ChatEntity,
    @Relation(
        parentColumn = "chatId",
        entityColumn = "userId",
        associateBy = Junction(ChatParticipantCrossRef::class)
    )
    val participants: List<ChatParticipantEntity> ,

    @Relation(
        parentColumn = "chatId",
        entityColumn = "senderId",
        entity = LastMessageView::class
    )
     val lastMessage: LastMessageView?
)


data class ChatInfoEntity(
    @Embedded
    val chat: ChatEntity,

    @Relation(
        parentColumn = "chatId",
        entityColumn = "userId",
        associateBy = Junction(ChatParticipantCrossRef::class)
    )
    val participants: List<ChatParticipantEntity>,

    @Relation(
        parentColumn = "chatId",
        entityColumn = "chatId",
        entity = ChatMessageEntity::class
    )
    val messagesWithSenders: List<MessageWithSender>,

    )