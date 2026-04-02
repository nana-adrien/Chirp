package empire.digiprem.com.chat.presentation.models

import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.presentation.util.UiText

sealed class  MessageUi(open val id: String) {
    data class  LocalUSerMessage(
        override val id:String,
        val content:String,
        val deliveryStatus:ChatMessageDeliveryStatus,
        val isMenuOpen:Boolean,
        val formattedSentTime:UiText
    ):MessageUi(id)

    data class OtherUserMessage(
        override val id:String,
        val content:String,
        val formattedSentTime:UiText,
        val sender: ChatParticipantUI
    ):MessageUi(id)

    data class DateSeparator(
        override val id: String,
        val date:UiText,
    ):MessageUi(id)
}