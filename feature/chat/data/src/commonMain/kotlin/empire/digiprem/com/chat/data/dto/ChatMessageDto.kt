package empire.digiprem.com.chat.data.dto

import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    val id:String,
    val chatId:String,
    val content:String,
    val createdAt:String,
    val senderId:String,
)