package empire.digiprem.com.chat.presentation.chat_list

import empire.digiprem.com.chat.presentation.models.ChatUi
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.presentation.util.UiText

data class ChatListState(
    val chats:List<ChatUi> = emptyList(),
    val error: UiText?=null,
    val localParticipant:ChatParticipantUI?=null,
    val isUserMenuOpen:Boolean=false,
    val showLogoutConfirmation:Boolean=false,
    val selectedChatId:String?=null,
    val isLoading: Boolean=false
)