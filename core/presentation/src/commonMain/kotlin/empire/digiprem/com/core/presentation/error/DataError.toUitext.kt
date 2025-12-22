package empire.digiprem.com.core.presentation.error

import chirp.core.presentation.generated.resources.Res
import chirp.core.presentation.generated.resources.error_bad_request
import chirp.core.presentation.generated.resources.error_conflict
import chirp.core.presentation.generated.resources.error_disk_full
import chirp.core.presentation.generated.resources.error_forbidden
import chirp.core.presentation.generated.resources.error_local_not_found
import chirp.core.presentation.generated.resources.error_not_internet
import chirp.core.presentation.generated.resources.error_payload_too_large
import chirp.core.presentation.generated.resources.error_request_timeout
import chirp.core.presentation.generated.resources.error_serialization
import chirp.core.presentation.generated.resources.error_server
import chirp.core.presentation.generated.resources.error_service_unavailable
import chirp.core.presentation.generated.resources.error_too_many_request
import chirp.core.presentation.generated.resources.error_unknown
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.presentation.util.UiText

fun DataError.toUiText(): UiText {
  val ressource= when(this){
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.NOT_FOUND -> Res.string.error_local_not_found
        DataError.Local.UNKNOWN ->  Res.string.error_unknown
        DataError.Remote.BAD_REQUEST -> Res.string.error_bad_request
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.UNAUTHORIZED ->  Res.string.error_unknown
        DataError.Remote.FORBIDDEN -> Res.string.error_forbidden
        DataError.Remote.NOT_FOUND -> Res.string.error_local_not_found
        DataError.Remote.CONFLICT -> Res.string.error_conflict
        DataError.Remote.TOO_MANY_REQUEST -> Res.string.error_too_many_request
        DataError.Remote.NOT_INTERNET -> Res.string.error_not_internet
        DataError.Remote.PAYLOAD_TOO_LARGE -> Res.string.error_payload_too_large
        DataError.Remote.SERVER_ERROR -> Res.string.error_server
        DataError.Remote.SERVER_UNAVAILABLE -> Res.string.error_service_unavailable
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }

      return UiText.Resource(ressource)
}