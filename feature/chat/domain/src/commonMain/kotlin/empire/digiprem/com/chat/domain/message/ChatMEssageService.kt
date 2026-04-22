package empire.digiprem.com.chat.domain.message

import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result

interface ChatMessageService {
    suspend fun fetchMessages(
        chatId:String,
        before:String?=null
    ):Result<List<ChatMessage>,DataError.Remote>
}