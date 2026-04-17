@file:OptIn(ExperimentalCoroutinesApi::class)

package empire.digiprem.com.chat.presentation.chat_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.chat.domain.chat.ChatRepository
import empire.digiprem.com.chat.presentation.models.toUi
import empire.digiprem.com.core.domain.auth.SessionStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatDetailViewModel(
    private val chatRepository: ChatRepository,
    private val sessionStorage: SessionStorage
) : ViewModel()
{
    private val _chatId = MutableStateFlow<String?>(null)
    private val chatInfoFlow = _chatId.flatMapLatest { chatId ->
        if (chatId != null) {
            chatRepository
                .getChatInfoById(chatId = chatId)
        } else emptyFlow()
    }

    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<ChatDetailEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatDetailState())

    private val stateWithMessage = combine(
        _state,
        chatInfoFlow,
        sessionStorage.observeAuthInfo()
    ) { currentState, chatInfo, authInfo ->
        if (authInfo == null) {
            return@combine ChatDetailState()
        }
        currentState.copy(
            chatUi = chatInfo.chat.toUi(authInfo.user.id),

            )
    }

    val state = _chatId
        .flatMapLatest { chatId ->
            if (chatId != null) {
                stateWithMessage
            } else {
                _state
            }
        }
        .onStart {
            if (!hasLoadedInitialData) {

                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatDetailState()
        )


    fun onAction(action: ChatDetailAction) {
        when (action) {
            is ChatDetailAction.OnSelectChat -> switchChat(action.chatId)
            else -> {}
        }
    }

    private fun switchChat(chatId: String?) {
        _chatId.update {
            chatId
        }
        viewModelScope.launch {
            chatId?.let {
                chatRepository.fetchChatById(chatId)
            }
        }
    }

}