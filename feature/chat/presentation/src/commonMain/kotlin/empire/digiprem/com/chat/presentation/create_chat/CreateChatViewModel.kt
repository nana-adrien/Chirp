package empire.digiprem.com.chat.presentation.create_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.chat.presentation.create_chat.CreateChatAction
import empire.digiprem.com.chat.presentation.create_chat.CreateChatEvent
import empire.digiprem.com.chat.presentation.create_chat.CreateChatState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class CreateChatViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<CreateChatEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(CreateChatState())

    val state = _state.onStart {
        if (!hasLoadedInitialData) {

            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = CreateChatState()
    )


    fun onAction(action: CreateChatAction) {
        when (action) {
            else -> {}
        }
    }

}