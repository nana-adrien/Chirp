package empire.digiprem.com.chat.presentation.models

import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.presentation.util.UiText

sealed interface  MessageUi {
    data class  LocalUSerMessage(
        val id:String,
        val content:String,
        val deliveryStatus:ChatMessageDeliveryStatus,
        val isMenuOpen:Boolean,
        val formattedSentTime:UiText
    ):MessageUi

    data class OtherUserMessage(
        val id:String,
        val content:String,
        val formattedSentTime:UiText,
        val sender: ChatParticipantUI
    ):MessageUi

    data class DateSeparator(
        val id: String,
        val date:UiText,
    ):MessageUi
}