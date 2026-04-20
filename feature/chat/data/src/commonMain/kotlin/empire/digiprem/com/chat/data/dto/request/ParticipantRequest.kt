package empire.digiprem.com.chat.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantRequest(
    val userIds:List<String>
)