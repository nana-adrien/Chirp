package empire.digiprem.com.core.designsystem.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpMultilineTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxHeightLines:Int=3,
    onKeyboardActions: () -> Unit={},
    bottomContent: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.extended.surfaceLower,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.extended.surfaceOutline,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BasicTextField(
            state = state,
            enabled = enabled,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.extended.textPrimary
            ),
            lineLimits = TextFieldLineLimits.MultiLine(
                minHeightInLines = 1,
                maxHeightInLines = maxHeightLines
            ),
            keyboardOptions = keyboardOptions,
            onKeyboardAction = { onKeyboardActions() },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.extended.textPrimary),
            decorator = { innerBox ->
                if (placeholder != null && state.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.extended.textPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                innerBox()
            }

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomContent(this)
        }

    }


}

@Preview
@Composable
fun ChirpMultilineTextFieldPreview() {

    ChirpTheme {
        ChirpMultilineTextField(
            state = rememberTextFieldState(
                initialText = "This is some text field content that maybe pans multiple lines"
            ),
            modifier = Modifier
                .width(300.dp)
                .heightIn(max = 150.dp)
            ,
            placeholder = null,
            bottomContent = {
                Spacer(modifier = Modifier.weight(1f))
                ChirpButton(
                    text = "Send",
                    onClick = {}
                )
            }
        )

    }


}