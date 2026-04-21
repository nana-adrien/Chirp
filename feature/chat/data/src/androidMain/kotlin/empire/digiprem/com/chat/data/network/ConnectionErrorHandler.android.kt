package empire.digiprem.com.chat.data.network

import empire.digiprem.com.chat.domain.models.ConnectionState
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.websocket.WebSocketException
import kotlinx.io.EOFException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

actual class ConnectionErrorHandler {
    actual fun getConnectionStateForError(cause: Throwable): ConnectionState {
        return  when(cause){
            is ClientRequestException,
                is WebSocketException,
                is SocketException,
                is SocketTimeoutException,
                is UnknownHostException,
                is SSLException,
                is EOFException-> ConnectionState.ERROR_NETWORK
            else -> ConnectionState.ERROR_UNKNOWN
        }
    }

    actual fun transformException(exception: Throwable): Throwable {
        return  exception
    }

    actual fun isRetriableError(cause: Throwable): Boolean {
       return when(cause){
           is WebSocketException,
           is SocketException,
           is SocketTimeoutException,
           is EOFException-> true
           else ->false
       }
    }
}