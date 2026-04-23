package empire.digiprem.com.chat.domain.chat

import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ConnectionState
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface  ChatConnectionClient {
    val chatMessage:Flow<ChatMessage>
    val connectionState :StateFlow<ConnectionState>
    //suspend fun sendChatMessage(message: ChatMessage):EmptyResult<DataError.Connection>
}