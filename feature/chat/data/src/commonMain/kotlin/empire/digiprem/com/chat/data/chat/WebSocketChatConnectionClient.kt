package empire.digiprem.com.chat.data.chat

import empire.digiprem.com.chat.data.dto.websocket.InComingWebSocketDto
import empire.digiprem.com.chat.data.dto.websocket.IncomingWebSocketType
import empire.digiprem.com.chat.data.dto.websocket.WebSocketMessageDto
import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.data.mapper.toEntity
import empire.digiprem.com.chat.data.mapper.toNewMessage
import empire.digiprem.com.chat.data.network.KtorWebSocketConnector
import empire.digiprem.com.chat.database.ChirpChatDatabase
import empire.digiprem.com.chat.domain.chat.ChatConnectionClient
import empire.digiprem.com.chat.domain.chat.ChatRepository
import empire.digiprem.com.chat.domain.error.ConnectionError
import empire.digiprem.com.chat.domain.message.MessageRepository
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.core.domain.auth.SessionStorage
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.onFailure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val webSocketConnector:KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val database: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository,
    private val applicationScope: CoroutineScope
) :ChatConnectionClient {

    override val chatMessage: Flow<ChatMessage> =webSocketConnector
        .messages
        .mapNotNull { parseInComingMessage(it) }
        .onEach { handleInComingMessage(it) }
        .filterIsInstance<InComingWebSocketDto.NewMessageDto>()
        .mapNotNull { message->
            database.chatMessageDao.getMessageById(message.id)?.toDomain()
        }
        .shareIn(
            applicationScope,
            SharingStarted.WhileSubscribed(5000)
        )

    override val connectionState= webSocketConnector.connectionState

    override suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError> {
        val outgoingDto=message.toNewMessage()
        val webSocketMessage= WebSocketMessageDto(
            type = outgoingDto.type.name,
            payload = json.encodeToString(outgoingDto)
        )
        val rawJsonPayload=json.encodeToString(webSocketMessage)
        return webSocketConnector
            .sendMessage(rawJsonPayload)
            .onFailure { error->
                messageRepository.updateMessageDeliveryStatus(
                    messageId = message.id,
                    status = ChatMessageDeliveryStatus.FAILED
                )
            }
    }

    private fun parseInComingMessage(message:WebSocketMessageDto):InComingWebSocketDto?{
        return try {
            when(val type=IncomingWebSocketType.valueOf(message.type) ){
                IncomingWebSocketType.NEW_MESSAGE->{
                    Json.decodeFromString<InComingWebSocketDto.NewMessageDto>(message.payload)
                }
                IncomingWebSocketType.MESSAGE_DELETED ->{
                    Json.decodeFromString<InComingWebSocketDto.MessageDeletedDto>(message.payload)
                }
                IncomingWebSocketType.PROFILE_PICTURE_UPDATED ->{
                    Json.decodeFromString<InComingWebSocketDto.ProfilePictureUpdated>(message.payload)
                }
                IncomingWebSocketType.CHAT_PARTICIPANTS_CHANGED ->{
                    Json.decodeFromString<InComingWebSocketDto.ChatParticipantsChangedDto>(message.payload)
                }

            }
        } catch (e:Exception){
            null
        }

    }

    private suspend fun handleInComingMessage(message: InComingWebSocketDto) {
        when(message){
            is InComingWebSocketDto.ChatParticipantsChangedDto -> refreshChat(message)
            is InComingWebSocketDto.MessageDeletedDto ->deleteMessage(message)
            is InComingWebSocketDto.NewMessageDto -> handleNewMessage(message)
            is InComingWebSocketDto.ProfilePictureUpdated -> updateProfilePicture(message)
        }
    }
    private suspend fun refreshChat(message: InComingWebSocketDto.ChatParticipantsChangedDto){
        chatRepository.fetchChatById(message.chatId)
    }
    private suspend fun deleteMessage(message: InComingWebSocketDto.MessageDeletedDto){
        database.chatMessageDao.deleteMessageById(message.messageId)
    }
    private suspend fun handleNewMessage(message: InComingWebSocketDto.NewMessageDto){
        val chatExists = database.chatDao.getChatById(message.chatId)!=null
        if (!chatExists){
            chatRepository.fetchChatById(message.chatId)
        }
        val entity=message.toEntity()
        database.chatMessageDao.upsertMassage(entity)
    }


    private suspend fun updateProfilePicture(message: InComingWebSocketDto.ProfilePictureUpdated){
        database.chatParticipantDao.updateProfilePictureUrl(
            userId=message.userId,
            newUrl = message.newUrl
        )
        val authInfo= sessionStorage.observeAuthInfo().firstOrNull()
        if (authInfo !=null){
            sessionStorage.set(
                info = authInfo.copy(
                    user = authInfo.user.copy(
                        profilePicture = message.newUrl
                    )
                )
            )
        }
    }
}