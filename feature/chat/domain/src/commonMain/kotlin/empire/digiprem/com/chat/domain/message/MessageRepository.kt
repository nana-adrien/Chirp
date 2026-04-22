package empire.digiprem.com.chat.domain.message

import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.chat.domain.models.MessageWithSender
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId:String,
        status:ChatMessageDeliveryStatus
    ):EmptyResult<DataError.Local>

    suspend fun fetchMessages(chatId:String,before:String?=null):Result<List<ChatMessage>,DataError>

    fun getMessagesForChat(chatId:String):Flow<List<MessageWithSender>>

}