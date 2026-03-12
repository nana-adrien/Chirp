package empire.digiprem.com.chat.presentation.chat_list_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import empire.digiprem.com.chat.presentation.chat_list.ChatListRoot
import empire.digiprem.com.chat.presentation.create_chat.CreateChatRoot
import empire.digiprem.com.core.designsystem.theme.extended
import empire.digiprem.com.core.presentation.util.DialogSheetScopedViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatListDetailAdaptiveLayout(
    onLogout: () -> Unit,
    chatListDetailViewModel: ChatListDetailViewModel = koinViewModel()
) {
    val sharedState by chatListDetailViewModel.state.collectAsStateWithLifecycle()
    val scaffoldDirective = createNoSpacingPaneScaffoldDirective()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = scaffoldDirective
    )
    val scope = rememberCoroutineScope()

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }

    ListDetailPaneScaffold(
        directive = scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.extended.surfaceLower),
        listPane = {
            AnimatedPane {
                ChatListRoot(
                    onChatClick = { chatUi ->
                        chatListDetailViewModel.onAction(
                            ChatListDetailAction.OnChatClick(chatId = chatUi.id)
                        )
                        scope.launch {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail
                            )
                        }
                    },
                    onProfileSettingsClick = {
                        chatListDetailViewModel.onAction(
                            ChatListDetailAction.OnProfileSettingsClick
                        )
                    },
                    onCreateChatClick = {
                        chatListDetailViewModel.onAction(
                            ChatListDetailAction.OnCreateChatClick
                        )
                    },
                    onConfirmLogoutClick = onLogout

                )
            }
        },
        detailPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    sharedState.selectedChatId?.let { selectedChatId ->
                        Text(
                            text = selectedChatId
                        )
                    }
                }
            }
        }
    )

    DialogSheetScopedViewModel(
        visible = sharedState.dialogState is DialogState.CreateChat
    ) {
        CreateChatRoot(
            onChatCreated = {
                chatListDetailViewModel.onAction(ChatListDetailAction.OnDismissCurrentDialogClick)
                chatListDetailViewModel.onAction(ChatListDetailAction.OnCreateChatClick)
                scope.launch {
                    scaffoldNavigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail
                    )
                }
            },
            onDismiss = {
                chatListDetailViewModel.onAction(ChatListDetailAction.OnDismissCurrentDialogClick)
            }
        )
    }
}