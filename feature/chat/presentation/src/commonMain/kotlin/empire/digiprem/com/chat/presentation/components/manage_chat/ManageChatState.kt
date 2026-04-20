package empire.digiprem.com.chat.presentation.components.manage_chat

import androidx.compose.foundation.text.input.TextFieldState
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.presentation.util.UiText

data class ManageChatState(
    val queryTextState:TextFieldState= TextFieldState(),
    val existingChatParticipants:List<ChatParticipantUI> = emptyList(),
    val selectedChatParticipants:List<ChatParticipantUI> = emptyList(),
    val isSearching:Boolean=false,
    val isLoadingParticipant: Boolean=false,
    val canAddParticipant:Boolean=false,
    val isCreatingChat:Boolean=false,
    val currentSearchResult:ChatParticipantUI?=null,
    val searchError:UiText?=null,
    val createChatError:UiText?=null
)