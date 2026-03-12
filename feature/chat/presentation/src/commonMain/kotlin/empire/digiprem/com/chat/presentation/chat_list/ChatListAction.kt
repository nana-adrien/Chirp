package empire.digiprem.com.chat.presentation.chat_list

import empire.digiprem.com.chat.presentation.models.ChatUi

sealed interface ChatListAction {
    data object OnUserAvatarClick:ChatListAction
    data object OnDismissUserMenu:ChatListAction
    data object OnProfileSettingsClick:ChatListAction
    data object OnLogoutClick:ChatListAction
    data object OnCreateChatClick:ChatListAction
    data object OnConfirmLogout:ChatListAction
    data object OnDismissLogoutDialog:ChatListAction
    data class OnChatClick(val chat:ChatUi):ChatListAction
}