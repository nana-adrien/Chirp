package empire.digiprem.com.chat.presentation.util

import chirp.feature.chat.presentation.generated.resources.*
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.offline
import empire.digiprem.com.chat.domain.models.ConnectionState
import empire.digiprem.com.core.presentation.util.UiText

fun ConnectionState.toUiText():UiText {
    val resource = when(this){
        ConnectionState.DISCONNECTED -> Res.string.offline
        ConnectionState.CONNECTING -> Res.string.reconnecting
        ConnectionState.CONNECTED -> Res.string.online
        ConnectionState.ERROR_NETWORK -> Res.string.network_error
        ConnectionState.ERROR_UNKNOWN -> Res.string.unknown_error
    }
    return UiText.Resource(resource)
}