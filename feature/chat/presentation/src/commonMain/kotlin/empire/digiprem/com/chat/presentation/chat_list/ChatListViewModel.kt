package empire.digiprem.com.chat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.chat.domain.chat.ChatRepository
import empire.digiprem.com.chat.presentation.chat_list.ChatListAction
import empire.digiprem.com.chat.presentation.chat_list.ChatListEvent
import empire.digiprem.com.chat.presentation.chat_list.ChatListState
import empire.digiprem.com.chat.presentation.mappers.toUi
import empire.digiprem.com.chat.presentation.models.toUi
import empire.digiprem.com.core.domain.auth.SessionStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
  private  val repository: ChatRepository,
   private val sessionStorage: SessionStorage
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<ChatListEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ChatListState())

    val state = combine(
        _state,
        repository.getChats(),
        sessionStorage.observeAuthInfo()
    ){currentState,chats,authInfo->
        if (authInfo==null){
            return@combine ChatListState()
        }
        currentState.copy(
            chats = chats.map { it.toUi(authInfo.user.id) },
            localParticipant =authInfo.user.toUi()
        )

    } .onStart {
        if (!hasLoadedInitialData) {
            loadChats()
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ChatListState()
    )


    fun onAction(action: ChatListAction) {
        when (action) {
            is ChatListAction.OnChatClick->{
                _state.update {
                    it.copy(
                        selectedChatId = action.chat.id
                    )
                }
            }
            else -> {}
        }
    }

    private fun loadChats(){
        viewModelScope.launch {
            repository.fetchChats()
        }
    }
}