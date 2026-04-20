package empire.digiprem.com.chat.presentation.manage_chat

import empire.digiprem.com.chat.domain.models.Chat

sealed interface ManageChatEvent {
    data class OnMembersAdded(val chat: Chat):ManageChatEvent
}