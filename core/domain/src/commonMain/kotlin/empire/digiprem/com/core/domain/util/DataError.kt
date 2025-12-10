package empire.digiprem.com.core.domain.util

sealed interface DataError: Error {
    enum class Remote: DataError{
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        TOO_MANY_REQUEST,
        NOT_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError{
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }

}


