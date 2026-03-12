package empire.digiprem.com.chat.domain.chat

import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(
        query:String
    ):Result<ChatParticipant,DataError.Remote>
}