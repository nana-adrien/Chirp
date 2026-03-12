package empire.digiprem.com.chat.presentation.chat_list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import chirp.feature.chat.presentation.generated.resources.do_you_want_to_logout
import chirp.feature.chat.presentation.generated.resources.do_you_want_to_logout_desc
import chirp.feature.chat.presentation.generated.resources.logout
import empire.digiprem.com.chat.presentation.chat_list.components.ChatListHeader
import empire.digiprem.com.chat.presentation.chat_list.components.EmptyChatSection
import empire.digiprem.com.chat.presentation.models.ChatUi
import empire.digiprem.com.core.designsystem.components.buttons.ChirpFloatingActionButton
import empire.digiprem.com.core.designsystem.components.dialogs.DestructionConfirmationDialog
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListRoot(
    onChatClick:(chat:ChatUi)->Unit,
    onConfirmLogoutClick:()->Unit,
    onCreateChatClick:()->Unit,
    onProfileSettingsClick:()->Unit,
    viewModel: ChatListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAction = viewModel::onAction
    val snackbarHostState = remember { SnackbarHostState() }
    ChatListScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = {action->
            when(action){
                is ChatListAction.OnChatClick -> onChatClick(action.chat)
                ChatListAction.OnConfirmLogout -> onConfirmLogoutClick()
                ChatListAction.OnCreateChatClick -> onCreateChatClick()
                ChatListAction.OnProfileSettingsClick -> onProfileSettingsClick()
                else->onAction(action)
            }
        }
    )
}

@Composable
fun ChatListScreen(
    state: ChatListState,
    snackbarHostState: SnackbarHostState,
    onAction: (ChatListAction) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.extended.surfaceLower,
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ChirpFloatingActionButton(
                onClick = {
                    onAction(ChatListAction.OnCreateChatClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(Res.string.create_chat)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChatListHeader(
                localParticipant = state.localParticipant,
                isUserMenuOpen = state.isUserMenuOpen,
                onUserAvatarClick = {
                    onAction(ChatListAction.OnUserAvatarClick)
                },
                onDismissMenu = {
                    onAction(
                        ChatListAction.OnDismissUserMenu
                    )
                },
                onLogoutClick = {
                    onAction(
                        ChatListAction.OnLogoutClick
                    )
                },
                onProfileSettingsClick = {
                    onAction(ChatListAction.OnProfileSettingsClick)
                }
            )
            when {
                state.isLoading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
                state.chats.isEmpty() -> {
                    EmptyChatSection(
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                    )
                }
                else ->{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ){
                        items(
                            items=state.chats,
                            key = {it.id}
                        ) {chatUi->
                            ChatListItemUi(
                                chat = chatUi,
                                isSelected = chatUi.id==state.selectedChatId,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onAction(ChatListAction.OnChatClick(chat= chatUi))
                                    }
                            )

                        }
                    }
                }
            }
        }

    }

    if (state.showLogoutConfirmation){
        DestructionConfirmationDialog(
            title = stringResource(Res.string.do_you_want_to_logout),
            description = stringResource(Res.string.do_you_want_to_logout_desc),
            confirmationButtonText = stringResource(Res.string.logout),
            cancelButtonsText = stringResource(Res.string.cancel),
            onDismiss = {
                onAction(ChatListAction.OnDismissLogoutDialog)
            },
            onCancelClick = {
                onAction(ChatListAction.OnDismissLogoutDialog)
            },
            onConfirmClick = {
                onAction(ChatListAction.OnConfirmLogout)
            },
        )
    }
}


@Composable
private fun ChatListPreview() {
    val snackbarHostState = remember { SnackbarHostState() }

    ChatListScreen(
        state = ChatListState(),
        snackbarHostState = snackbarHostState,
        onAction = {

        }
    )
}


@Preview
@Composable
private fun ChatListLightThemePreview() {
    ChirpTheme {
        ChatListPreview()
    }
}

@Preview
@Composable
private fun ChatListDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatListPreview()
    }
}

