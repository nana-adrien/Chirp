package empire.digiprem.com.chat.data.mapper

import empire.digiprem.com.chat.data.dto.ChatDto
import empire.digiprem.com.chat.database.entites.ChatEntity
import empire.digiprem.com.chat.database.entites.ChatInfoEntity
import empire.digiprem.com.chat.database.entites.ChatWithParticipants
import empire.digiprem.com.chat.database.entites.MessageWithSender
import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.chat.domain.models.ChatInfo
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.chat.domain.models.ChatParticipant
import kotlin.time.Instant

typealias DataMessageWithSender=MessageWithSender
typealias DomainMessageWithSender=empire.digiprem.com.chat.domain.models.MessageWithSender



fun ChatDto.toDomain(): Chat {
    return Chat(
        id = id,
        participant = participants.map { it.toDomain() },
        lastMessage = lastMessage?.toDomain(),
        lastActivityAt = Instant.parse(lastActivityAt),
    )
}

fun ChatWithParticipants.toDomain(): Chat {
    return Chat(
        id = chat.chatId,
        participant = participants.map { it.toDomain() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}

fun Chat.toEntity(): ChatEntity {
    return ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds()
    )
}

fun ChatEntity.toDomain(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage?=null
): Chat {
    return Chat(
        id = chatId,
        participant =participants ,
        lastMessage =lastMessage ,
        lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt)
    )
}

fun DataMessageWithSender.toDomain():DomainMessageWithSender{
    return DomainMessageWithSender(
        message = message.toDomain(),
        sender = sender.toDomain(),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(this.message.deliveryStatus)
    )
}


fun ChatInfoEntity.toDomain(): ChatInfo {
    return ChatInfo(
        chat = chat.toDomain(
            participants = participants.map { it.toDomain() },
        ),
        message = messagesWithSenders.map {it.toDomain()}
    )
}