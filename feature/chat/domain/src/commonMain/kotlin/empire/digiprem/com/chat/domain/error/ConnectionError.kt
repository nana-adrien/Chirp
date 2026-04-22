package empire.digiprem.com.chat.domain.error

import empire.digiprem.com.core.domain.util.Error

enum class ConnectionError:Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}