package empire.digiprem.com.chat.presentation.chat_detail

import empire.digiprem.com.core.presentation.util.UiText

sealed interface ChatDetailEvent {
    data object OnChatLeft:ChatDetailEvent
    data class OnError(val error:UiText):ChatDetailEvent
}