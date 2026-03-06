package empire.digiprem.com.chat.presentation.create_chat.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import empire.digiprem.com.chat.domain.models.ChatParticipant
import empire.digiprem.com.core.designsystem.components.avatar.ChirpAvatarPhoto
import empire.digiprem.com.core.designsystem.theme.extended
import empire.digiprem.com.core.designsystem.theme.titleXSmall
import empire.digiprem.com.core.presentation.util.DeviceConfiguration
import empire.digiprem.com.core.presentation.util.currentDeviceConfigure

@Composable
fun ColumnScope.ChatParticipantsSelectionSection(
    selectedParticipant: List<ChatParticipant>,
    searchResult:ChatParticipant?=null,
    modifier: Modifier=Modifier,
){

    val deviceConfiguration = currentDeviceConfigure()
    val rootHeightModifier=when(deviceConfiguration){
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE ,
        DeviceConfiguration.DESKTOP -> Modifier.animateContentSize().heightIn(min=200.dp,max=300.dp)
        else ->Modifier.weight(1f)
    }

    Box(
        modifier=rootHeightModifier.then(modifier)
    ){
        LazyColumn(
            modifier=Modifier.fillMaxWidth()
        ) {
            searchResult?.let{
                item {
                    ChatParticipantListItem(
                        participantUi = searchResult,

                    )
                }
            }
            if (selectedParticipant.isNotEmpty() && searchResult ==null){
                items(selectedParticipant, key = {it.userId}){participant->
                    ChatParticipantListItem(
                        participantUi = participant,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
    }


}


@Composable
fun ChatParticipantListItem(
    participantUi: ChatParticipant,
    modifier: Modifier=Modifier
){
    Row(
        modifier=modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){

        ChirpAvatarPhoto(
            displayText = participantUi.initials,
            imageUrl = participantUi.profilePictureUrl
        )
        Text(
            text = participantUi.username,
            style = MaterialTheme.typography.titleXSmall,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }

}