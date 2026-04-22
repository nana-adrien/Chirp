package empire.digiprem.com.chat.data.dto.websocket

import kotlinx.serialization.Serializable


enum class IncomingWebSocketType {
    NEW_MESSAGE,
    MESSAGE_DELETED,
    PROFILE_PICTURE_UPDATED,
    CHAT_PARTICIPANTS_CHANGED
}


@Serializable
sealed class InComingWebSocketDto(
    private val type: IncomingWebSocketType
) {
    @Serializable
    data class NewMessageDto(
        val id: String,
        val chatId: String,
        val content: String,
        val senderId: String,
        val createdAt: String
    ) : InComingWebSocketDto(IncomingWebSocketType.NEW_MESSAGE)

    @Serializable
    data class MessageDeletedDto(
        val messageId:String,
        val chatId:String
    ):InComingWebSocketDto(IncomingWebSocketType.MESSAGE_DELETED)

    @Serializable
    data class ProfilePictureUpdated(
        val userId:String,
        val newUrl:String?
    ):InComingWebSocketDto(IncomingWebSocketType.PROFILE_PICTURE_UPDATED)

    @Serializable
    data class ChatParticipantsChangedDto(
        val chatId:String
    ):InComingWebSocketDto(IncomingWebSocketType.CHAT_PARTICIPANTS_CHANGED)
}