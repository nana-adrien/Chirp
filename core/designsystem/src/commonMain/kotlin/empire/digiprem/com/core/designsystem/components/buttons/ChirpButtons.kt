package empire.digiprem.com.core.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChirpButtonStyle {
    PRIMARY,
    DESTRUCTIVE_PRIMARY,
    SECONDARY,
    DESTRUCTIVE_SECONDARY,
    TEXT
}

@Composable
fun ChirpButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val colors = when (style) {
        ChirpButtonStyle.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
            disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill
        )

        ChirpButtonStyle.DESTRUCTIVE_PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
            disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill
        )

        ChirpButtonStyle.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.extended.textSecondary,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
            disabledContainerColor = Color.Transparent
        )

        ChirpButtonStyle.DESTRUCTIVE_SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.error,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
            disabledContainerColor = Color.Transparent,
        )

        ChirpButtonStyle.TEXT -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
            disabledContainerColor = Color.Transparent,
        )
    }

    val defaultBoarderStroke = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.extended.disabledOutline
    )
    val border = when {
        style == ChirpButtonStyle.PRIMARY && !enabled -> defaultBoarderStroke
        style == ChirpButtonStyle.SECONDARY ->defaultBoarderStroke
        style == ChirpButtonStyle.DESTRUCTIVE_PRIMARY   && !enabled -> defaultBoarderStroke
        style == ChirpButtonStyle.DESTRUCTIVE_SECONDARY -> {
            val borderColor = if (enabled) {
                MaterialTheme.colorScheme.extended.destructiveSecondaryOutline
            } else {
                MaterialTheme.colorScheme.extended.disabledOutline
            }
            BorderStroke(
                width = 1.dp,
                color = borderColor
            )
        }

        else -> null
    }


    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        border = border,
    ) {
        Box(
            modifier =Modifier.padding(6.dp),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(15.dp).alpha(alpha = if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = Color.Black
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            ) {
                leadingIcon?.invoke()
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

    }

}

@Preview
@Composable
fun ChirpPrimaryButtonPreview() {
    Column {

        ChirpTheme(
            darkTheme = true
        ){
            ChirpButton(
                text = "Hello word!",
                onClick = {},
                style = ChirpButtonStyle.PRIMARY
            )
        }
        ChirpTheme(
            darkTheme = false
        ){
            ChirpButton(
                isLoading = true,
                text = "Hello word!",
                onClick = {},
                style = ChirpButtonStyle.PRIMARY
            )
        }
    }

}

@Preview
@Composable
fun ChirpSecondaryButtonPreview() {
    Column {

        ChirpTheme(
            darkTheme = true
        ){  ChirpButton(
            text = "Hello word!",
            onClick = {},
            style = ChirpButtonStyle.SECONDARY
        )}
        ChirpTheme(
            darkTheme = false
        ){  ChirpButton(
            isLoading = true,
            text = "Hello word!",
            onClick = {},
            style = ChirpButtonStyle.SECONDARY
        )}
    }
}

@Preview
@Composable
fun ChirpDestructivePrimaryButtonPreview() {
    Column {
        ChirpTheme(
            darkTheme = true
        ){ ChirpButton(
            text = "Hello word!",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_PRIMARY
        )}
        ChirpTheme(
            darkTheme = false
        ){ ChirpButton(
            isLoading = true,
            text = "Hello word!",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_PRIMARY
        )}
    }

}

@Preview
@Composable
fun ChirpDestructiveSecondaryButtonPreview() {
    Column {
        ChirpTheme(
            darkTheme = true
        ){   ChirpButton(
            text = "Hello word!",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_SECONDARY
        )}

        ChirpTheme(
            darkTheme = false
        ){   ChirpButton(
            isLoading = true,
            text = "Hello word!",
            onClick = {},
            style = ChirpButtonStyle.DESTRUCTIVE_SECONDARY
        )}
    }

}


