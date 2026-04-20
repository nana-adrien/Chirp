package empire.digiprem.com.chat.presentation.manage_chat

import androidx.lifecycle.ViewModel
import empire.digiprem.com.chat.presentation.components.manage_chat.ManageChatAction
import empire.digiprem.com.chat.presentation.components.manage_chat.ManageChatState
import empire.digiprem.com.chat.presentation.create_chat.CreateChatEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ManageChatViewModel:ViewModel(){

    private val _eventChannel = Channel<ManageChatEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _state= MutableStateFlow(ManageChatState())
    val state=_state.asStateFlow()


    fun onAction(action:ManageChatAction){
        when(action){
            ManageChatAction.OnAddClick ->{}
            ManageChatAction.OnDismissDialog -> {}
            ManageChatAction.OnManageChatClick ->{}
        }
    }
}
