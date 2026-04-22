package empire.digiprem.com.chat.presentation.mappers

import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.chat.domain.models.MessageWithSender
import empire.digiprem.com.chat.presentation.models.MessageUi
import empire.digiprem.com.chat.presentation.util.DateUtils
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI

fun MessageWithSender.toUi(
    localUserId:String,): MessageUi {
    val isFromLocalUser=this.sender.userId==localUserId

    return if(isFromLocalUser){
        MessageUi.LocalUSerMessage(
            id=message.id,
            content=message. content,
            deliveryStatus =message.deliveryStatus,
            isMenuOpen = false,
            formattedSentTime =DateUtils.formatMessageTime(instant =message.createAt)
        )
    }else{
        MessageUi.OtherUserMessage(
            id=message.id,
            content=message.content,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createAt),
            sender =sender.toUi()
        )
    }
}