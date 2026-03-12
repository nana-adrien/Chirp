package empire.digiprem.com.chat.presentation.mappers

import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI

fun ChatParticipant.toUi():ChatParticipantUI{
    return ChatParticipantUI(
        id=userId,
        username = username,
        imageUrl = profilePictureUrl,
        initials = initials,
    )
}