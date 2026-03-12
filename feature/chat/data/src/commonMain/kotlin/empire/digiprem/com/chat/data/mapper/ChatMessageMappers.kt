package empire.digiprem.com.chat.data.mapper

import empire.digiprem.com.chat.data.dto.ChatMessageDto
import empire.digiprem.com.chat.domain.models.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        senderId = senderId,
        createAt = Instant.parse(createdAt)
    )
}