package empire.digiprem.com.chat.data.chat

import empire.digiprem.com.chat.data.dto.ChatDto
import empire.digiprem.com.chat.data.dto.request.CreateChatRequest
import empire.digiprem.com.chat.data.dto.request.ParticipantRequest
import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.domain.chat.ChatService
import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.core.data.networking.delete
import empire.digiprem.com.core.data.networking.get
import empire.digiprem.com.core.data.networking.post
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.asEmptyResult
import empire.digiprem.com.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatService(
    private val httpClient: HttpClient
) :ChatService{
    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return  httpClient.post<CreateChatRequest,ChatDto>(
            route = "/chat",
            body = CreateChatRequest(otherUserIds)
        ).map { it.toDomain() }
    }

    override suspend fun getChats(): Result<List<Chat>, DataError.Remote> {
       return httpClient.get<List<ChatDto>>(
           route = "/chat"
       ).map {chatDtos->
           chatDtos.map { it.toDomain() }
       }
    }

    override suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote> {
        return httpClient.get<ChatDto>(
            route = "/chat/$chatId"
        ).map {
            it.toDomain()
        }
    }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> {
        return httpClient.delete<Unit>(
            route = "/chat/$chatId/leave",
        ).asEmptyResult()
    }

    override suspend fun addParticipantsToChat(
        chatId: String,
        userIds: List<String>
    ): Result<Chat, DataError.Remote> {
        return  httpClient.post<ParticipantRequest, ChatDto>(
            route = "/chat/$chatId/add",
            body = ParticipantRequest(
                userIds=userIds
            )
        ).map { it.toDomain() }
    }
}