package empire.digiprem.com.chat.domain.models

import kotlin.time.Instant

data class ChatMessage(
    val id:String,
    val chatId:String,
    val content:String,
    val createAt:Instant,
    val senderId:String,
    val deliveryStatus: ChatMessageDeliveryStatus
)