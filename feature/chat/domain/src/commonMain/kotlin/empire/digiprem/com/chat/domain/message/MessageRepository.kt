package empire.digiprem.com.chat.domain.message

import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId:String,
        status:ChatMessageDeliveryStatus
    ):EmptyResult<DataError.Local>

}