package empire.digiprem.com.chat.presentation.manage_chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.create_chat
import chirp.feature.chat.presentation.generated.resources.save
import empire.digiprem.com.chat.presentation.components.ManageChatScreen
import empire.digiprem.com.chat.presentation.components.manage_chat.ManageChatAction
import empire.digiprem.com.chat.presentation.create_chat.CreateChatEvent
import empire.digiprem.com.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import empire.digiprem.com.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManageChatRoot(
    viewModel: ManageChatViewModel= koinViewModel(),
    onMembersAdded:()->Unit,
    onDismiss:()->Unit,
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events){event->
        when(event){
            is ManageChatEvent.OnMembersAdded->onMembersAdded()
            else->Unit
        }
    }
    ChirpAdaptiveDialogSheetLayout(
        onDismiss = onDismiss
    ){

        ManageChatScreen(
            headerText = stringResource(Res.string.chat_members),
            primaryButtonText = stringResource(Res.string.save),
            state=state,
            onAction = {action->
                when(action){
                    ManageChatAction.OnDismissDialog->onDismiss()
                    else->Unit
                }
                viewModel.onAction(action)
            }
        )

    }

}