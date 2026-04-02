package empire.digiprem.com.chat.presentation.chat_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.chat.presentation.chat_detail.ChatDetailAction
import empire.digiprem.com.chat.presentation.chat_detail.ChatDetailEvent
import empire.digiprem.com.chat.presentation.chat_detail.ChatDetailState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ChatDetailViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<ChatDetailEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatDetailState())

    val state = _state.onStart {
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

            else -> {}
        }
    }

}