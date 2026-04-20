package empire.digiprem.com.chat.presentation.chat_detail


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import empire.digiprem.com.chat.domain.models.ChatMessage
import empire.digiprem.com.chat.domain.models.ChatMessageDeliveryStatus
import empire.digiprem.com.chat.presentation.chat_detail.components.ChatDetailHeader
import empire.digiprem.com.chat.presentation.chat_detail.components.MessageBox
import empire.digiprem.com.chat.presentation.chat_detail.components.MessageList
import empire.digiprem.com.chat.presentation.components.ChatHeader
import empire.digiprem.com.chat.presentation.models.ChatUi
import empire.digiprem.com.chat.presentation.models.MessageUi
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.designsystem.components.layout.ChirpSnackBarScaffold
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import empire.digiprem.com.core.presentation.util.ObserveAsEvents
import empire.digiprem.com.core.presentation.util.UiText
import empire.digiprem.com.core.presentation.util.clearFocusOnTap
import empire.digiprem.com.core.presentation.util.currentDeviceConfigure
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatDetailRoot(
    chatId: String?,
    isDetailPresent: Boolean,
    onBack: () -> Unit,
    onChatMembersClick:()->Unit,
    viewModel: ChatDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onAction = viewModel::onAction


    val snackbarState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ChatDetailEvent.OnChatLeft -> onBack()
            is ChatDetailEvent.OnError -> {
                snackbarState.showSnackbar(event.error.asStringAsync())
            }
        }
    }

    LaunchedEffect(chatId) {
        viewModel.onAction(ChatDetailAction.OnSelectChat(chatId))
    }

    BackHandler(
        enabled = !isDetailPresent
    ) {
        viewModel.onAction(ChatDetailAction.OnSelectChat(null))
        onBack()
    }
    ChatDetailScreen(
        state = state,
        isDetailPresent = isDetailPresent,
        snackbarState = snackbarState,
        onAction ={ action->
            when(action){
                is ChatDetailAction.OnChatMembersClick->onChatMembersClick()
                else->Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ChatDetailScreen(
    state: ChatDetailState,
    isDetailPresent: Boolean,
    snackbarState: SnackbarHostState,
    onAction: (ChatDetailAction) -> Unit
) {
    val configuration = currentDeviceConfigure()
    val messageListState = rememberLazyListState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackbarHost(snackbarState)
            },
            contentWindowInsets = WindowInsets.safeDrawing,
            contentColor = if (!configuration.isWideScreen) {
                MaterialTheme.colorScheme.surface
            } else MaterialTheme.colorScheme.extended.surfaceLower
        )
        { innerPadding ->
            Box(
                modifier = Modifier
                    .clearFocusOnTap()
                    .padding(innerPadding)
                    .then(
                        if (configuration.isWideScreen) {
                            Modifier.padding(horizontal = 8.dp)
                        } else Modifier
                    )

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DynamicRoundedCornerColumn(
                        isCornersRounded = configuration.isWideScreen,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    {
                        ChatHeader {
                            ChatDetailHeader(
                                chatUi = state.chatUi,
                                isDetailPresent = isDetailPresent,
                                isChatOptionsDropDownOpen = state.isChatOptionsOpen,
                                onChatOptionsClick = {
                                    onAction(ChatDetailAction.OnChatOptionsClick)
                                },
                                onDismissChatOptions = {
                                    onAction(ChatDetailAction.OnDismissChatOption)
                                },
                                onManageChatClick = {
                                    onAction(ChatDetailAction.OnChatMembersClick)
                                },
                                onLeaveChatClick = {
                                    onAction(ChatDetailAction.OnLeaveChatClick)
                                },
                                onBackClick = {
                                    onAction(ChatDetailAction.OnBackClick)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        MessageList(
                            messages = state.messages,
                            listState = messageListState,
                            onMessageLongClick = { message ->
                                onAction(ChatDetailAction.OnMessageLongClick(message))
                            },
                            onDeleteMessageClick = { message ->
                                onAction(ChatDetailAction.OnMessageLongClick(message))
                            },
                            onMessageRetryClick = { message ->
                                onAction(ChatDetailAction.OnMessageLongClick(message))
                            },
                            onDismissMessageMenu = {
                                onAction(ChatDetailAction.OnDismissMessageMenu)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()

                        )
                        AnimatedVisibility(
                            visible = !configuration.isWideScreen && state.chatUi != null
                        ) {
                            MessageBox(
                                messageTextFieldState = state.messageTextFieldState,
                                isTextInputEnabled = state.canSendMessage,
                                connectionState = state.connectionState,
                                onSendClick = {
                                    onAction(ChatDetailAction.OnSendMessageClick)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 8.dp,
                                        horizontal = 16.dp
                                    )
                            )
                        }


                    }
                    if (configuration.isWideScreen) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    AnimatedVisibility(
                        visible = configuration.isWideScreen && state.chatUi != null
                    ) {
                        DynamicRoundedCornerColumn(
                            isCornersRounded = configuration.isWideScreen
                        ) {
                            MessageBox(
                                messageTextFieldState = state.messageTextFieldState,
                                isTextInputEnabled = state.canSendMessage,
                                connectionState = state.connectionState,
                                onSendClick = {
                                    onAction(ChatDetailAction.OnSendMessageClick)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }

                    }
                }
            }
        }


}


@Composable
private fun DynamicRoundedCornerColumn(
    isCornersRounded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = if (isCornersRounded) 4.dp else 0.dp,
                shape = if (isCornersRounded) RoundedCornerShape(24.dp) else RectangleShape,
                spotColor = Color.Black.copy(alpha = 3f),
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = if (isCornersRounded) RoundedCornerShape(24.dp) else RectangleShape
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
private fun ChatDetailPreview(isDetailPresent: Boolean, messages: List<MessageUi> = emptyList()) {
    ChatDetailScreen(
        state = ChatDetailState(
            messageTextFieldState = rememberTextFieldState(
                initialText = "This is a new message !"
            ),
            canSendMessage = true,
            messages = messages,
            chatUi = ChatUi(
                id = "1",
                localParticipant = ChatParticipantUI(
                    id = "1",
                    initials = "Pg",
                    username = "Philipp"
                ),
                otherParticipants = listOf(
                    ChatParticipantUI(
                        id = "2",
                        initials = "Na",
                        username = "Nana"
                    ),
                    ChatParticipantUI(
                        id = "3",
                        initials = "AD",
                        username = "Adrien"
                    ),
                ),
                lastMessage = ChatMessage(
                    id = "1",
                    chatId = "1",
                    content = "j'ai juste envie d'ecrire que tout fonctionne bien" +
                            "su plusieur ligne pour tester aussi le decoupage",
                    createAt = Clock.System.now(),
                    senderId = "1",
                    deliveryStatus = ChatMessageDeliveryStatus.SENT
                ),
                lastMessageSenderUsername = "Nana Adrien"
            )
        ),
        isDetailPresent = isDetailPresent,
        snackbarState = remember { SnackbarHostState() },
        onAction = {

        }
    )
}


@Preview
@Composable
private fun ChatDetailLightThemePreview() {
    ChirpTheme {
        ChatDetailPreview(true)
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun ChatDetailDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatDetailPreview(
            false,
            messages = (1..20).map {
                val showLocalMessage = Random.nextBoolean()
                if (showLocalMessage) {
                    MessageUi.LocalUSerMessage(
                        id = Uuid.random().toString(),
                        content = "Hollo world",
                        deliveryStatus = ChatMessageDeliveryStatus.SENT,
                        isMenuOpen = false,
                        formattedSentTime = UiText.DynamicString("Friday,Aug 20"),
                    )
                } else {
                    MessageUi.OtherUserMessage(
                        id = Uuid.random().toString(),
                        content = "Hollo world",
                        sender = ChatParticipantUI(
                            id = "1",
                            username = "John",
                            initials = "JO"
                        ),
                        formattedSentTime = UiText.DynamicString("Friday,Aug 20")
                    )
                }
            },
        )
    }
}

