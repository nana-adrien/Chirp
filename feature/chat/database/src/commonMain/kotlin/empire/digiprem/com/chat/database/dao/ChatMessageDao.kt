package empire.digiprem.com.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import empire.digiprem.com.chat.database.entites.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM chatmessageentity WHERE messageId= :messageId")
    suspend fun getMessageById(messageId:String):ChatMessageEntity?
}