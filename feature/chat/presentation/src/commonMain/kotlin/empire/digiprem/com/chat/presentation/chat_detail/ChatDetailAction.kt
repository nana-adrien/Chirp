package empire.digiprem.com.chat.presentation.chat_detail

import empire.digiprem.com.chat.presentation.models.MessageUi

sealed interface ChatDetailAction {
    data object OnSendMessageClick : ChatDetailAction
    data object OnScrollToTop : ChatDetailAction
    data object OnDismissChatOption : ChatDetailAction
    data object OnDismissMessageMenu : ChatDetailAction
    data object OnBackClick : ChatDetailAction
    data object OnChatOptionsClick : ChatDetailAction
    data object OnChatMembersClick : ChatDetailAction
    data object OnLeaveChatClick : ChatDetailAction
    data  class OnSelectChat(val chatId:String?) : ChatDetailAction
    data  class OnDeleteMessageClick(val message:MessageUi.LocalUSerMessage) : ChatDetailAction
    data  class OnMessageLongClick(val message:MessageUi.LocalUSerMessage) : ChatDetailAction
    data  class OnRetryClick(val message:MessageUi.LocalUSerMessage) : ChatDetailAction

}