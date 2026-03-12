package empire.digiprem.com.chat.presentation.models

import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI

data class ChatUi(
    val id: String,
    val localParticipant: ChatParticipantUI,
    val otherParticipants: List<ChatParticipantUI>,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername:String?

)