package empire.digiprem.com.chat.data.chat

import empire.digiprem.com.chat.data.dto.ChatDto
import empire.digiprem.com.chat.data.dto.request.CreateChatRequest
import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.domain.chat.ChatService
import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.core.data.networking.post
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatService(private val httpClient: HttpClient) :ChatService{
    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return  httpClient.post<CreateChatRequest,ChatDto>(
            route = "/chat",
            body = CreateChatRequest(otherUserIds)
        ).map { it.toDomain() }
    }
}