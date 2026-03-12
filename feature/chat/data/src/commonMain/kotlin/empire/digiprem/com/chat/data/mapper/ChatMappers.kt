package empire.digiprem.com.chat.data.mapper

import empire.digiprem.com.chat.data.dto.ChatDto
import empire.digiprem.com.chat.domain.models.Chat
import kotlin.time.Instant

fun ChatDto.toDomain(): Chat {
    return Chat(
        id = id,
        participant = participants.map { it.toDomain() },
        lastMessage = lastMessage?.toDomain(),
        lastActivityAt = Instant.parse(lastActivityAt),
        )
}