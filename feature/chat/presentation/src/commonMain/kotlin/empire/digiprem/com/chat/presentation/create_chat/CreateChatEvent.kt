package empire.digiprem.com.chat.presentation.create_chat

sealed interface CreateChatEvent {
    object OnInitEvent : CreateChatEvent
}