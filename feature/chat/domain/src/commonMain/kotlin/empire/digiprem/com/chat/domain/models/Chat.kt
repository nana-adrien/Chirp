package empire.digiprem.com.chat.domain.models

import kotlin.time.Instant

data class Chat(
    val id:String,
    val participant: List<ChatParticipant>,
    val lastActivityAt:Instant,
    val lastMessage:String?
)
