package empire.digiprem.com.chat.data.message

import empire.digiprem.com.chat.data.dto.ChatMessageDto
import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.database.dao.ChatMessageDao
import empire.digiprem.com.chat.domain.message.ChatMessageService
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.core.data.networking.get
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatMessageService(
    private val httpClient: HttpClient
):ChatMessageService {
    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError.Remote> {
        return  httpClient.get<List<ChatMessageDto>>(
            route="/chat/$chatId/messages",
            queryParams = buildMap {
                this["pageSize"]=ChatMessageConstants.PAGE_SIZE
                if (before!=null){
                    this["before"]=before
                }
            }
        ).map {it.map { unit->unit.toDomain() }}
    }
}