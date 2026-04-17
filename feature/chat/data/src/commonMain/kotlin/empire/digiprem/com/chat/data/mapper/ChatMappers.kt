package empire.digiprem.com.chat.data.mapper

import empire.digiprem.com.chat.data.dto.ChatDto
import empire.digiprem.com.chat.database.entites.ChatEntity
import empire.digiprem.com.chat.database.entites.ChatWithParticipants
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
fun ChatWithParticipants.toDomain():Chat{
    return Chat(
        id = chat.chatId,
        participant = participants.map { it.toDomain() },
        lastActivityAt =Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}

fun  Chat.toEntity(): ChatEntity {
    return  ChatEntity(
        chatId =id,
        lastActivityAt =lastActivityAt.toEpochMilliseconds()
    )
}