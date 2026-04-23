package empire.digiprem.com.chat.data.dto.websocket

import kotlinx.serialization.Serializable


enum class IncomingWebSocketType {
    NEW_MESSAGE,
    MESSAGE_DELETED,
    PROFILE_PICTURE_UPDATED,
    CHAT_PARTICIPANTS_CHANGED
}


@Serializable
sealed interface InComingWebSocketDto {
    @Serializable
    data class NewMessageDto(
        val id: String,
        val chatId: String,
        val content: String,
        val senderId: String,
        val createdAt: String,
        val type: IncomingWebSocketType=IncomingWebSocketType.NEW_MESSAGE
    ) : InComingWebSocketDto

    @Serializable
    data class MessageDeletedDto(
        val messageId:String,
        val chatId:String,
        val type: IncomingWebSocketType=IncomingWebSocketType.MESSAGE_DELETED
    ):InComingWebSocketDto

    @Serializable
    data class ProfilePictureUpdated(
        val userId:String,
        val newUrl:String?,
        val type: IncomingWebSocketType=IncomingWebSocketType.PROFILE_PICTURE_UPDATED
    ):InComingWebSocketDto

    @Serializable
    data class ChatParticipantsChangedDto(
        val chatId:String,
        val type: IncomingWebSocketType=IncomingWebSocketType.CHAT_PARTICIPANTS_CHANGED
    ):InComingWebSocketDto
}