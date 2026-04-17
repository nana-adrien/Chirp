package empire.digiprem.com.chat.domain.chat

import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result

interface ChatService {
    suspend fun createChat(
        otherUserIds:List<String>
    ):Result<Chat,DataError.Remote>

    suspend fun getChats():Result<List<Chat>,DataError.Remote>

    suspend fun getChatById(chatId:String):Result<Chat,DataError.Remote>

}