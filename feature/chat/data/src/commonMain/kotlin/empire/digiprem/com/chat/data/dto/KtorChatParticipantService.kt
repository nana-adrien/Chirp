package empire.digiprem.com.chat.data.dto

import empire.digiprem.com.chat.data.mapper.toDomain
import empire.digiprem.com.chat.domain.chat.ChatParticipantService
import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.core.data.networking.get
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatParticipantService(private val httpClient: HttpClient): ChatParticipantService {
    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
       return httpClient.get<ChatParticipantDto>(
           route="/participants",
           queryParams=mapOf(
               "query" to query
           )
       ).map { it.toDomain()}
    }
}