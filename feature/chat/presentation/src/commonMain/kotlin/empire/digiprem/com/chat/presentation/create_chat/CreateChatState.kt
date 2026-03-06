package empire.digiprem.com.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.core.presentation.util.UiText

data class CreateChatState(
    val queryTextState:TextFieldState= TextFieldState(),
    val selectedChatParticipants:List<ChatParticipant> = emptyList(),
    val isAddingParticipants:Boolean=false,
    val isLoadingParticipant: Boolean=false,
    val canAddParticipant:Boolean=false,
    val isCreatingChat:Boolean=false,
    val currentSearchResult:ChatParticipant?=null,
    val searchError:UiText?=null
)