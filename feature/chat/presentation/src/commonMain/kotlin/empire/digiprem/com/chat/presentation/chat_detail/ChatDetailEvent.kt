package empire.digiprem.com.chat.presentation.chat_detail

sealed interface ChatDetailEvent {
    object OnInitEvent : ChatDetailEvent
}