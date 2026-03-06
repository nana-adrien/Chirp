package empire.digiprem.com.chat.presentation.chat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Composable
fun ChatDetailRoot() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(text="Chat Detail Screen")
    }
}

@Serializable
object ChatDetail