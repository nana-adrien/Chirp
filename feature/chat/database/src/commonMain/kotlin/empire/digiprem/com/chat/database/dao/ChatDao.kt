package empire.digiprem.com.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

import androidx.room.Upsert
import empire.digiprem.com.chat.database.entites.ChatEntity
import empire.digiprem.com.chat.database.entites.ChatInfoEntity
import empire.digiprem.com.chat.database.entites.ChatMessageEntity
import empire.digiprem.com.chat.database.entites.ChatParticipantCrossRef
import empire.digiprem.com.chat.database.entites.ChatParticipantEntity
import empire.digiprem.com.chat.database.entites.ChatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsetChat(chat: ChatEntity)

    @Upsert
    suspend fun upsetChats(chats: List<ChatEntity>)

    @Query("DELETE FROM chatentity WHERE chatId=:chatId")
    suspend fun deleteChatById(chatId: String)

    @Query("SELECT * FROM chatentity ORDER BY lastActivityAt DESC")
    @Transaction
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>

/*    @Query(
        """
        SELECT DISTINCT c.*
        FROM chatentity c 
        JOIN chatparticipantcrossref cprc ON c.chatId = cprc.chatId
        WHERE cprc.isActive=1
        ORDER BY  lastActivityAt DESC
    """
    )
    @Transaction
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>*/

    @Query("SELECT * FROM chatentity WHERE chatId=:id")
    suspend fun getChatById(id: String): ChatWithParticipants?

    @Query("DELETE FROM chatentity")
    suspend fun deleteAllChats()

    @Query("SELECT chatId FROM chatentity")
    suspend fun getAllChatIds(): List<String>

    @Transaction
    suspend fun deleteChatsByIds(chatIds: List<String>) {
        chatIds.forEach { chatId ->
            deleteChatById(chatId)
        }
    }

    @Query("SELECT COUNT(*) FROM chatentity")
    fun getChatCount(): Flow<Int>

    @Query(
        """
        SELECT p.*
        FROM chatparticipantentity p
        JOIN chatparticipantcrossref cpcr ON p.userId=cpcr.userId
        WHERE cpcr.chatId =:chatId AND cpcr.isActive=true
        ORDER BY p.username
    """
    )
    @Transaction
    fun getActiveParticipantsByChatId(chatId: String): Flow<List<ChatParticipantEntity>>

            /*
    @Query("""
        SELECT c.*
        FROM chatentity c
        JOIN chatparticipantcrossref cpcr ON cpcr.chatId = c.chatId
         WHERE c.chatId= :chatId AND cpcr.isActive =true
         """)*/
    @Query("""
        SELECT c.*
        FROM chatentity c
         WHERE c.chatId= :chatId
         """)
    fun getChatInfoById(chatId: String): Flow<ChatInfoEntity>


    @Transaction
    suspend fun upsertChatWithParticipantsAndCrossRefs(
        chat: ChatEntity,
        participants: List<ChatParticipantEntity>,
        // crossRefs:List<ChatParticipantCrossRef>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao
    ) {
        upsetChat(chat)
        participantDao.upsertParticipants(participants)
        val crossRef = participants.map { participant ->
            ChatParticipantCrossRef(
                chatId = chat.chatId,
                userId = participant.userId,
                isActive = true
            )
        }
        crossRefDao.upsertCrossRefs(crossRef)
        crossRefDao.syncChatParticipants(chat.chatId, participants)
    }

    @Transaction
    suspend fun upsertChatsWithParticipantsAndCrossRefs(
        chats: List<ChatWithParticipants>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao
    ) {
        upsetChats(chats.map { it.chat })
        val allParticipants = chats.flatMap { it.participants }
        participantDao.upsertParticipants(allParticipants)
        val allCrossRef = chats.flatMap { chatWithParticipants ->
            chatWithParticipants.participants.map { participant ->
                ChatParticipantCrossRef(
                    chatId = chatWithParticipants.chat.chatId,
                    userId = participant.userId,
                    isActive = true
                )
            }
        }
        crossRefDao.upsertCrossRefs(allCrossRef)

        chats.forEach { chat ->
            crossRefDao.syncChatParticipants(
                chatId = chat.chat.chatId,
                participants = chat.participants
            )
        }

    }

    @Transaction
    suspend fun upsertChatsWithParticipantsAndCrossRefs(
        chats: List<ChatWithParticipants>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao,
        messageDao: ChatMessageDao
    ) {
        upsetChats(chats.map { it.chat })

        val serverChatIds = chats.map { it.chat.chatId }
        val localChatIds = getAllChatIds()
        val staleChatIds = localChatIds - serverChatIds


        chats.forEach { chat ->
            chat.lastMessage?.run {
                messageDao.upsertMassage(
                    ChatMessageEntity(
                        messageId = messageId,
                        chatId = chatId,
                        content = content,
                        senderId = senderId,
                        timestamp = timestamp,
                        deliveryStatus = deliveryStatus
                    )
                )
            }
        }

        val allParticipants = chats.flatMap { it.participants }
        participantDao.upsertParticipants(allParticipants)
        val allCrossRef = chats.flatMap { chatWithParticipants ->
            chatWithParticipants.participants.map { participant ->
                ChatParticipantCrossRef(
                    chatId = chatWithParticipants.chat.chatId,
                    userId = participant.userId,
                    isActive = true
                )
            }
        }
        crossRefDao.upsertCrossRefs(allCrossRef)

        chats.forEach { chat ->
            crossRefDao.syncChatParticipants(
                chatId = chat.chat.chatId,
                participants = chat.participants
            )
        }


        deleteChatsByIds(staleChatIds)

    }

}
