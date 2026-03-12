package empire.digiprem.com.chat.presentation.models

import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.chat.presentation.mappers.toUi

fun Chat.toUi(localParticipantId:String):ChatUi{
    val (local,other)=participant.partition { it.userId==localParticipantId }
    return ChatUi(
        id = id,
        lastMessage = lastMessage,
        localParticipant = local.first().toUi(),
        otherParticipants =other.map { it.toUi() },
        lastMessageSenderUsername = participant.find {
            it.userId==lastMessage?.senderId
        }?.username
    )
}