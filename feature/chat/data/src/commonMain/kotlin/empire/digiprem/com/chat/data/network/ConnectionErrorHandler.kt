package empire.digiprem.com.chat.data.network

import empire.digiprem.com.chat.domain.models.ConnectionState

expect class ConnectionErrorHandler {
    fun getConnectionStateForError(cause:Throwable): ConnectionState
    fun transformException(exception:Throwable):Throwable
    fun isRetriableError(cause:Throwable):Boolean
}