package empire.digiprem.com.chat.data.chat

import empire.digiprem.com.chat.data.dto.websocket.WebSocketMessageDto
import empire.digiprem.com.chat.data.mapper.toNewMessage
import empire.digiprem.com.chat.data.network.KtorWebSocketConnector
import empire.digiprem.com.chat.database.ChirpChatDatabase
import empire.digiprem.com.chat.domain.chat.ChatConnectionClient
import empire.digiprem.com.chat.domain.chat.ChatRepository
import empire.digiprem.com.chat.domain.error.ConnectionError
import empire.digiprem.com.chat.domain.message.MessageRepository
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.chat.domain.models.ConnectionState
import empire.digiprem.com.core.domain.auth.SessionStorage
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClient(
    private val webSocketConnector:KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val database: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository
) :ChatConnectionClient {

    override val chatMessage: Flow<ChatMessage> = emptyFlow()
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

}