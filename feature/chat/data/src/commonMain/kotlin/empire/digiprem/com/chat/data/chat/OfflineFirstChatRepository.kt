package empire.digiprem.com.chat.data.chat

import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.data.mapper.toEntity
import empire.digiprem.com.chat.data.mapper.toLastMessageView
import empire.digiprem.com.chat.database.ChirpChatDatabase
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithActiveParticipants()
            .map { chatWithParticipantsList ->
                chatWithParticipantsList.map { it.toDomain() }
            }
    }

    override  fun getChatInfoById(chatId: String): Flow<ChatInfo> {
        return db.chatDao
            .getChatInfoById(chatId)
            .firstOrNull()
            .map{it.toDomain()}
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
      return  chatService
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


}