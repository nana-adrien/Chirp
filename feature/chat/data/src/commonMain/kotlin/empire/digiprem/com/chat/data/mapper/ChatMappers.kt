package empire.digiprem.com.chat.data.mapper

import empire.digiprem.com.chat.data.dto.ChatParticipantDto
import empire.digiprem.com.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toDomain():ChatParticipant{
    return  ChatParticipant(
        userId=userId,
        username=username,
        profilePictureUrl=profilePictureUrl
    )
}