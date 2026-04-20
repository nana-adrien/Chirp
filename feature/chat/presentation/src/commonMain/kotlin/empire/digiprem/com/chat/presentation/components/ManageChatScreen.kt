package empire.digiprem.com.chat.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import empire.digiprem.com.chat.domain.models.Chat
import empire.digiprem.com.chat.presentation.components.manage_chat.ManageChatAction
import empire.digiprem.com.chat.presentation.components.manage_chat.ManageChatState
import empire.digiprem.com.chat.presentation.create_chat.components.ChatMemberSearchTextSection
import empire.digiprem.com.chat.presentation.create_chat.components.ChatParticipantsSelectionSection
import empire.digiprem.com.chat.presentation.create_chat.components.ManageChatButtonSection
import empire.digiprem.com.chat.presentation.create_chat.components.ManageChatHeaderRow
import empire.digiprem.com.core.designsystem.components.brand.ChirpHorizontalDivider
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButtonStyle
import empire.digiprem.com.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.presentation.util.DeviceConfiguration
import empire.digiprem.com.core.presentation.util.ObserveAsEvents
import empire.digiprem.com.core.presentation.util.clearFocusOnTap
import empire.digiprem.com.core.presentation.util.currentDeviceConfigure
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ManageChatScreen(
    headerText:String,
    primaryButtonText:String,
    state: ManageChatState,
    onAction: (ManageChatAction) -> Unit
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = imeHeight > 0
    val configuration = currentDeviceConfigure()

    val shouldHideHeader = configuration == DeviceConfiguration.MOBILE_LANDSCAPE
            || (isKeyboardVisible && configuration != DeviceConfiguration.DESKTOP) || isTextFieldFocused

    Column(
        modifier = Modifier
            .clearFocusOnTap()
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {

        AnimatedVisibility(
            visible = !shouldHideHeader
        ) {
            Column {
                ManageChatHeaderRow(
                    title =headerText,
                    onCloseClick = {
                        onAction(ManageChatAction.OnDismissDialog)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ChirpHorizontalDivider()
            }
        }
        ChatMemberSearchTextSection(
            queryState = state.queryTextState,
            onAddClick = {
                onAction(ManageChatAction.OnAddClick)
            },
            isSearchEnabled = state.canAddParticipant,
            isLoading = state.isLoadingParticipant,
            modifier = Modifier
                .fillMaxWidth(),
            error = state.searchError,
            onFocusChanged = {
                isTextFieldFocused = it
            }
        )
        ChirpHorizontalDivider()
        ChatParticipantsSelectionSection(
            existingParticipants=state.existingChatParticipants,
            selectedParticipant = state.selectedChatParticipants,
            modifier = Modifier.fillMaxWidth(),
            searchResult = state.currentSearchResult
        )
        ChirpHorizontalDivider()
        ManageChatButtonSection(
            error = state.createChatError?.asString(),
            modifier = Modifier.fillMaxWidth(),
            primaryButton = {
                ChirpButton(
                    text =primaryButtonText,
                    onClick = { onAction(ManageChatAction.OnManageChatClick) },
                    isLoading = state.isCreatingChat,
                    enabled = state.selectedChatParticipants.isNotEmpty()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.cancel),
                    onClick = { onAction(ManageChatAction.OnDismissDialog) },
                    style = ChirpButtonStyle.SECONDARY
                )
            }
        )
    }

}


@Composable
private fun CreateChatPreview() {
    ManageChatScreen(
        headerText =   stringResource(Res.string.create_chat),
        primaryButtonText =   stringResource(Res.string.create_chat),
        state = ManageChatState(),
        onAction = {

        }
    )
}


@Preview
@Composable
private fun CreateChatLightThemePreview() {
    ChirpTheme {
        CreateChatPreview()
    }
}

@Preview
@Composable
private fun CreateChatDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        CreateChatPreview()
    }
}

