package empire.digiprem.com.chat.presentation.chat_list

sealed interface ChatListEvent {
    object OnInitEvent : ChatListEvent
}