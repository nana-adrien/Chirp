package empire.digiprem.com.chat.data.mapper

import empire.digiprem.com.chat.data.dto.ChatMessageDto
import empire.digiprem.com.chat.database.entites.ChatMessageEntity
import empire.digiprem.com.chat.database.view.LastMessageView
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        senderId = senderId,
        createAt = Instant.parse(createdAt),
        deliveryStatus = ChatMessageDeliveryStatus.SENT
    )
}
fun ChatMessageEntity.toDomain(): ChatMessage {
    return ChatMessage(
        id = messageId,
        chatId = chatId,
        content = content,
        senderId = senderId,
        createAt = Instant.fromEpochMilliseconds(timestamp),
        deliveryStatus = ChatMessageDeliveryStatus.SENT
    )
}

fun LastMessageView.toDomain(): ChatMessage {
    return ChatMessage(
        id = messageId,
        chatId = chatId,
        content = content,
        senderId = senderId,
        createAt = Instant.fromEpochMilliseconds(timestamp),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(this.deliveryStatus)
    )
}

fun ChatMessage.toLastMessageView(): LastMessageView {
    return LastMessageView(
        messageId = id,
        chatId = chatId,
        content = content,
        senderId = senderId,
        timestamp = createAt.toEpochMilliseconds(),
        deliveryStatus = deliveryStatus.name
    )
}

fun LastMessageView.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        messageId = messageId,
        chatId = chatId,
        content = content,
        senderId = senderId,
        timestamp = timestamp,
        deliveryStatus = deliveryStatus
    )
}

fun ChatMessage.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        messageId = id,
        chatId = chatId,
        content = content,
        senderId = senderId,
        timestamp = createAt.toEpochMilliseconds(),
        deliveryStatus = deliveryStatus.name
    )
}