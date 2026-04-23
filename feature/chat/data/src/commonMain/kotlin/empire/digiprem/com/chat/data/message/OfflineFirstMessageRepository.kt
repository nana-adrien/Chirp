package empire.digiprem.com.chat.data.message

import empire.digiprem.com.chat.data.database.safeDatabaseUpdate
import empire.digiprem.com.chat.data.dto.websocket.OutgoingWebSocketDto
import empire.digiprem.com.chat.data.dto.websocket.WebSocketMessageDto
import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.data.mapper.toEntity
import empire.digiprem.com.chat.data.mapper.toWebSocketDto
import empire.digiprem.com.chat.data.network.KtorWebSocketConnector
import empire.digiprem.com.chat.database.ChirpChatDatabase
import empire.digiprem.com.chat.domain.message.ChatMessageService
import empire.digiprem.com.chat.domain.message.MessageRepository
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.chat.domain.models.MessageWithSender
import empire.digiprem.com.chat.domain.models.OutgoingNewMessage
import empire.digiprem.com.core.domain.auth.SessionStorage
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.asEmptyResult
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val webSocketConnector: KtorWebSocketConnector,
    private val applicationScope:CoroutineScope
) : MessageRepository {
    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            database.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }.asEmptyResult()
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
        return chatMessageService
            .fetchMessages(chatId, before)
            .onSuccess { messages ->
                return safeDatabaseUpdate {
                    val entities = messages.map { it.toEntity() }
                    database.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize = ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before == null // only sync for most recent page
                    )
                    messages
                }
            }
    }

    override suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError> {
        val localUser = sessionStorage.observeAuthInfo().first()?.user
            ?: return Result.Failure(DataError.Local.NOT_FOUND)
        return safeDatabaseUpdate {
            val dto = message.toWebSocketDto()

            val entity = dto.toEntity(
                senderId = localUser.id,
                deliveryStatus = ChatMessageDeliveryStatus.SENDING
            )
            database.chatMessageDao.upsertMassage(entity)
            webSocketConnector
                .sendMessage(dto.toJsonPayload())
                .onFailure {error->
                    applicationScope.launch {
                        database.chatMessageDao.upsertMassage(
                            dto.toEntity(
                                senderId = localUser.id,
                                deliveryStatus = ChatMessageDeliveryStatus.FAILED
                            )
                        )
                    }.join()
                }

        }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return database
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { it.map { it.toDomain() } }
    }

    private fun OutgoingWebSocketDto.NewMessage.toJsonPayload(): String {
        val webSocketMessage = WebSocketMessageDto(
            type = type.name,
            payload = json.encodeToString(this)
        )
        return json.encodeToString(webSocketMessage)
    }
}