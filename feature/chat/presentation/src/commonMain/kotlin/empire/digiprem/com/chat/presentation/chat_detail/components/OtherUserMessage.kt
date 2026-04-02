package empire.digiprem.com.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import empire.digiprem.com.chat.presentation.models.MessageUi
import empire.digiprem.com.core.designsystem.components.avatar.ChirpAvatarPhoto
import empire.digiprem.com.core.designsystem.components.chat.ChirpChatBubble
import empire.digiprem.com.core.designsystem.components.chat.TrianglePosition
import empire.digiprem.com.core.designsystem.theme.extended

@Composable
fun OtherUserMessage(
    message:MessageUi.OtherUserMessage,
    color: Color= MaterialTheme.colorScheme.extended.surfaceHigher,
    modifier: Modifier=Modifier
) {
    Row(
        modifier=modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChirpAvatarPhoto(
            displayText = message.sender.initials,
            imageUrl = message.sender.imageUrl
        )
        ChirpChatBubble(
            messageContent = message.content,
            color = color,
            sender = message.sender.username,
            trianglePosition = TrianglePosition.LEFT,
            formattedDateTime = message.formattedSentTime.asString()
        )
    }

}