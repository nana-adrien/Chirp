package empire.digiprem.com.chat.domain.chat

import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.chat.domain.models.ChatInfo
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats():Flow<List<Chat>>
      fun  getChatInfoById(chatId:String):Flow<ChatInfo>
    suspend fun fetchChats():Result<List<Chat>,DataError.Remote>
    suspend fun fetchChatById(chatId:String):EmptyResult<DataError.Remote>
    suspend fun createChat(otherUserIds:List<String>):Result<Chat,DataError.Remote>
    suspend fun leaveChat(chatId:String):EmptyResult<DataError.Remote>

}