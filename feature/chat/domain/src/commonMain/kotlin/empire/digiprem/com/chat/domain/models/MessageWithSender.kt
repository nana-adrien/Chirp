package empire.digiprem.com.chat.domain.models

data class MessageWithSender (
    val message: ChatMessage,
    val sender:ChatParticipant,
    val deliveryStatus: ChatMessageDeliveryStatus?
)

