package empire.digiprem.com.chat.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import empire.digiprem.com.core.designsystem.theme.extended

@Composable
fun getChatBubbleColorsForUser(
    userId:String
):Color {
    val colorPool= with(MaterialTheme .colorScheme.extended){
        listOf(
            cakeRed,
            cakeGreen,
            cakePink,
            cakeOrange,
            cakeYellow,
            cakeMint,
            cakeViolet,
            cakePurple,
            cakeBlue,
        )
    }
    val index=userId.hashCode().toUInt() % colorPool.size.toUInt()

    return colorPool[index.toInt()]

}