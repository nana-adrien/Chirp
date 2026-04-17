package empire.digiprem.com.chat.domain.chat

import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats():Flow<List<Chat>>
    suspend fun fetchChats():Result<List<Chat>,DataError.Remote>
}