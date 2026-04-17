package empire.digiprem.com.chat.presentation.mappers

import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.domain.auth.User

fun ChatParticipant.toUi():ChatParticipantUI{
    return ChatParticipantUI(
        id=userId,
        username = username,
        imageUrl = profilePictureUrl,
        initials = initials,
    )
}
fun User.toUi():ChatParticipantUI{
    return ChatParticipantUI(
        id=id,
        username = username,
        imageUrl = profilePicture,
        initials =username.take(2) ,
    )
}