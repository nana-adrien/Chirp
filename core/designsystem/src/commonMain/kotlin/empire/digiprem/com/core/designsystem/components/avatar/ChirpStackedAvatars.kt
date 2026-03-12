package empire.digiprem.com.core.designsystem.components.avatar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpStackedAvatars(
    avatars:List<ChatParticipantUI>,
    modifier: Modifier=Modifier,
    size: AvatarSize=AvatarSize.SMALL,
    maxVisible:Int=2,
    overlapPercentage:Float=0.4f
) {
    val overlapOffset=-(size.dp * overlapPercentage)

    val visibleAvatars=avatars.take(maxVisible)
    val remainingCount=(avatars.size-maxVisible).coerceAtLeast(0)

    Row(
        modifier=modifier,
        horizontalArrangement = Arrangement.spacedBy(overlapOffset),
        verticalAlignment = Alignment.CenterVertically
    ){
        visibleAvatars.forEach { avatarUI ->
            ChirpAvatarPhoto(
                displayText = avatarUI.initials,
                size = size,
                imageUrl = avatarUI.imageUrl
            )
        }
        if (remainingCount>0){
            ChirpAvatarPhoto(
                displayText = "$remainingCount+",
                textColor = MaterialTheme.colorScheme.primary
            )
        }

    }

}

@Preview
@Composable
fun ChirpStackedAvatars(){
    ChirpTheme {
        ChirpStackedAvatars(
            maxVisible = 3,
            avatars = listOf(
                ChatParticipantUI(
                    id = "1",
                    username = "Pl-coding",
                    initials = "PL"
                ),
                ChatParticipantUI(
                    id = "2",
                    username = "Jhon",
                    initials = "JO"
                ),
                ChatParticipantUI(
                    id = "3",
                    username = "Sara",
                    initials = "SA"
                ),
                ChatParticipantUI(
                    id = "4",
                    username = "Ciril",
                    initials = "CI"
                ),
            )
        )

    }

}
