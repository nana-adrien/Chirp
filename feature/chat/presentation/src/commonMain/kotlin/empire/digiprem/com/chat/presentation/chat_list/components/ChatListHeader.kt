package empire.digiprem.com.chat.presentation.chat_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.log_out_icon
import chirp.core.designsystem.generated.resources.logo_chirp
import chirp.core.designsystem.generated.resources.users_icon
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.logout
import chirp.feature.chat.presentation.generated.resources.profile_settings
import empire.digiprem.com.chat.presentation.components.ChatHeader
import empire.digiprem.com.core.designsystem.components.avatar.ChatParticipantUI
import empire.digiprem.com.core.designsystem.components.avatar.ChirpAvatarPhoto
import empire.digiprem.com.core.designsystem.components.dropdown.ChirpDropDownMenu
import empire.digiprem.com.core.designsystem.components.dropdown.DropDownMenuItem
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import chirp.core.designsystem.generated.resources.Res as DesignSystemRes

@Composable
fun ChatListHeader(
    localParticipant: ChatParticipantUI?,
    isUserMenuOpen: Boolean = false,
    onUserAvatarClick: () -> Unit,
    onDismissMenu: () -> Unit,
    onLogoutClick: () -> Unit,
    onProfileSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ChatHeader(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = vectorResource(DesignSystemRes.drawable.logo_chirp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = "Chirp",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.extended.textPrimary
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            ProfileAvatarSection(
                localParticipant = localParticipant,
                isMenuOpen = isUserMenuOpen,
                onClick = onUserAvatarClick,
                onDismissMenu = onDismissMenu,
                onProfileSettingsClick = onProfileSettingsClick,
                onLogoutClick = onLogoutClick,
            )
        }


    }
}

@Composable
fun ProfileAvatarSection(
    localParticipant: ChatParticipantUI?,
    isMenuOpen: Boolean,
    onClick: () -> Unit,
    onProfileSettingsClick: () -> Unit,
    onDismissMenu: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (localParticipant != null) {
            ChirpAvatarPhoto(
                displayText = localParticipant.initials,
                imageUrl = localParticipant.imageUrl,
                onclick = onClick
            )
        }

        ChirpDropDownMenu(
            isOpen=isMenuOpen,
            onDismiss = onDismissMenu,
            items = listOf(
                DropDownMenuItem(
                    title = stringResource(Res.string.profile_settings),
                    icon = vectorResource(DesignSystemRes.drawable.users_icon),
                    contentColor = MaterialTheme.colorScheme.extended.textSecondary,
                    onClick = onProfileSettingsClick
                ),
                DropDownMenuItem(
                    title = stringResource(Res.string.logout),
                    icon = vectorResource(DesignSystemRes.drawable.log_out_icon),
                    contentColor = MaterialTheme.colorScheme.extended.destructiveHover,
                    onClick = onLogoutClick
                ),
            )
        )
    }
}
