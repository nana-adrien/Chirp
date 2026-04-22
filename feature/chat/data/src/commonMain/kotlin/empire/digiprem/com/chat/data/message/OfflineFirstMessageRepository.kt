package empire.digiprem.com.chat.data.message

import empire.digiprem.com.chat.data.database.safeDatabaseUpdate
import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.data.mapper.toEntity
import empire.digiprem.com.chat.database.ChirpChatDatabase
import empire.digiprem.com.chat.domain.message.ChatMessageService
import empire.digiprem.com.chat.domain.message.MessageRepository
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.chat.domain.models.MessageWithSender
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.asEmptyResult
import empire.digiprem.com.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService
):MessageRepository {
    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            database.chatMessageDao.updateDeliveryStatus(
                messageId=messageId,
                status=status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }.asEmptyResult()
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
      return  chatMessageService
            .fetchMessages(chatId,before)
            .onSuccess { messages->
                return safeDatabaseUpdate {
                    val entities=messages.map { it.toEntity() }
                    database.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId=chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize =ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before==null // only sync for most recent page
                    )
                    messages
                }
            }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return database
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { it.map {it.toDomain() }}
    }
}