package empire.digiprem.com.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
private fun ChirpTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    title: String? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFocusChange: (Boolean) -> Unit = {},
) {


    ChirpTextFieldLayout(
        title = title,
        supportingText = supportingText,
        isError = isError,
        onFocusChange = onFocusChange,
        modifier = modifier
    ) {styleModifier,interactionSource->
        BasicTextField(
            state = state,
            enabled = enabled,
            modifier = styleModifier,
            lineLimits = if (singleLine) {
                TextFieldLineLimits.SingleLine
            } else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.extended.textPlaceholder
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorator = { innerBox ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.text.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerBox()
                }
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun ChirpTextFieldLightThemePreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Test@Email.com",
            title = "Email",
            supportingText = "please enter your email "
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun DisabledChirpTextFieldLightThemePreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            enabled = false,
            modifier = Modifier.width(300.dp),
            placeholder = "Test@Email.com",
            title = "Email",
            supportingText = "please enter your email "
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorChirpTextFieldLightThemePreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            enabled = false,
            modifier = Modifier.width(300.dp),
            placeholder = "Test@Email.com",
            title = "Email",
            isError = true,
            supportingText = "this is not de valide email "
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChirpTextFieldDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpTextField(
            state = rememberTextFieldState(initialText = "text@email.com"),
            modifier = Modifier.width(300.dp),
            placeholder = "Test@Email.com",
            title = "Email",
            supportingText = "please enter your email "
        )
    }
}