package empire.digiprem.com.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import empire.digiprem.com.chat.database.entites.ChatMessageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
interface ChatMessageDao {

    @Upsert
    suspend fun upsertMassage(message:ChatMessageEntity)


    @Upsert
    suspend fun upsertMassages(messages:List<ChatMessageEntity>)

    @Query("DELETE FROM chatmessageentity WHERE messageId=:messageId ")
    suspend fun deleteMessageById(messageId:String)

    @Query("DELETE FROM chatmessageentity WHERE messageId  IN (:messageIds) ")
    suspend fun deleteMessagesByIds(messageIds:List<String>)

    @Query("SELECT * FROM chatmessageentity WHERE chatId =:chatId ORDER BY timestamp DESC")
    fun getMessagesByChatId(chatId:String):Flow<List<ChatMessageEntity>>

    @Query("""
        SELECT *
        FROM chatmessageentity 
        WHERE chatId =:chatId 
        ORDER BY timestamp DESC
        LIMIT :limit
    """)
    fun getMessagesByChatIdLimited(chatId:String,limit:Int):Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chatmessageentity WHERE messageId= :messageId")
    suspend fun getMessageById(messageId:String):ChatMessageEntity?

    @Query(
        """
            UPDATE chatmessageentity    
            SET deliveryStatus= :status, deliveryStatusTimestamp= :timestamp
        """
    )
    suspend fun updateDeliveryStatus(messageId:String,status:String,timestamp:Long)

    @Transaction
    suspend fun upsertMessagesAndSyncIfNecessary(
        chatId: String,
        serverMessages:List<ChatMessageEntity>,
        pageSize:Int,
        shouldSync:Boolean=false
    ){
        val localMessages=getMessagesByChatIdLimited(
            chatId=chatId,
            limit = pageSize
        ).first()
        upsertMassages(serverMessages)
        if (!shouldSync){
            return
        }

        val serverIds=serverMessages.map { it.messageId }.toSet()
        val messagesToDelete=localMessages.filter { localMessages->
            val missingOnServer=localMessages.messageId !in serverIds
            val isSent=localMessages.deliveryStatus=="SENT"
            missingOnServer && isSent
        }

        val messageIds=messagesToDelete.map { it.messageId }
        deleteMessagesByIds(messageIds)
    }
}