package empire.digiprem.com.chat.presentation.create_chat

import empire.digiprem.com.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat):CreateChatEvent
}