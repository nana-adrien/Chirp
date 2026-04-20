package empire.digiprem.com.chat.data.chat

import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.data.mapper.toEntity
import empire.digiprem.com.chat.data.mapper.toLastMessageView
import empire.digiprem.com.chat.database.ChirpChatDatabase
import empire.digiprem.com.chat.database.entites.ChatInfoEntity
import empire.digiprem.com.chat.database.entites.ChatParticipantEntity
import empire.digiprem.com.chat.database.entites.ChatWithParticipants
import empire.digiprem.com.chat.domain.chat.ChatRepository
import empire.digiprem.com.chat.domain.chat.ChatService
import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.chat.domain.models.ChatInfo
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.asEmptyResult
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithParticipants()
            .map { allChatsWithParticipants ->
                supervisorScope {
                    allChatsWithParticipants
                        .map { chatWithParticipants ->
                            async {
                                ChatWithParticipants(
                                    chat = chatWithParticipants.chat,
                                    participants = chatWithParticipants.participants
                                        .onLyActive(chatWithParticipants.chat.chatId),
                                    lastMessage = chatWithParticipants.lastMessage
                                )
                            }
                        }.awaitAll()
                        .map { it.toDomain() }
                }
            }
    }

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> {
        return db.chatDao
            .getChatInfoById(chatId)
            .filterNotNull()
            .map { chatInfo ->
                ChatInfoEntity(
                    chat = chatInfo.chat,
                    participants = chatInfo.participants.onLyActive(chatInfo.chat.chatId),
                    messagesWithSenders = chatInfo.messagesWithSenders
                )
            }
            .map { it.toDomain() }
    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        return chatService
            .getChats()
            .onSuccess { chats ->
                val chatWithParticipants = chats.map { chat ->
                    ChatWithParticipants(
                        chat = chat.toEntity(),
                        participants = chat.participant.map { it.toEntity() },
                        lastMessage = chat.lastMessage?.toLastMessageView()
                    )
                }
                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatWithParticipants,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                    participantDao = db.chatParticipantDao,
                    messageDao = db.chatMessageDao
                )

            }
            .onFailure {

            }
    }

    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> {
        return chatService
            .getChatById(chatId)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participant.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao
                )
            }.asEmptyResult()
    }

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return chatService
            .createChat(otherUserIds)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participant.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao
                )
            }
    }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> {
        return chatService.leaveChat(chatId)
            .onSuccess {
                db.chatDao.deleteChatById(chatId)
            }
    }


    private suspend fun List<ChatParticipantEntity>.onLyActive(chatId: String): List<ChatParticipantEntity> {
        val actionParticipantIds = db.chatDao
            .getActiveParticipantsByChatId(chatId)
            .first()
            .map { it.userId }
        return this.filter { it.userId in actionParticipantIds }
    }
}