package empire.digiprem.com.chat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.chat.presentation.chat_list.ChatListAction
import empire.digiprem.com.chat.presentation.chat_list.ChatListEvent
import empire.digiprem.com.chat.presentation.chat_list.ChatListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ChatListViewModel : ViewModel() {
    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<ChatListEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatListState())

    val state = _state.onStart {
        if (!hasLoadedInitialData) {

            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ChatListState()
    )


    fun onAction(action: ChatListAction) {
        when (action) {
            else -> {}
        }
    }

}