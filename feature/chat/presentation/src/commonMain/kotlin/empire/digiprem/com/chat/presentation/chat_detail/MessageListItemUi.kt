package empire.digiprem.com.chat.presentation.chat_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import empire.digiprem.com.chat.presentation.chat_detail.components.DeteSeparator
import empire.digiprem.com.chat.presentation.chat_detail.components.LocalUserMessage
import empire.digiprem.com.chat.presentation.chat_detail.components.OtherUserMessage
import empire.digiprem.com.chat.presentation.models.MessageUi

@Composable
fun  MessageListItemUi(
    messageUi: MessageUi,
    onMessageLongClick:()->Unit,
    onDismissMessageMenu:()->Unit,
    onRetryClick:()->Unit,
    onDeleteClick:()->Unit,
    modifier:Modifier=Modifier
) {
    Box(
        modifier=modifier
    ){
        when(messageUi){
            is MessageUi.DateSeparator -> DeteSeparator(
                date = messageUi.date.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            is MessageUi.LocalUSerMessage ->LocalUserMessage(
                isMenuOpen =messageUi. isMenuOpen,
                message=messageUi,
                onMessageLongClick= onMessageLongClick,
                onDismissMessageMenu=onDismissMessageMenu,
                onDeleteClick=onDeleteClick,
                onRetryClick=onRetryClick,
            )
            is MessageUi.OtherUserMessage -> OtherUserMessage(
                message=messageUi
            )
        }

    }


}

