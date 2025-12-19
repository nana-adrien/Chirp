package empire.digiprem.com.core.designsystem.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import empire.digiprem.com.core.presentation.util.DeviceConfiguration
import empire.digiprem.com.core.presentation.util.currentDeviceConfigure
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ChirpAdaptativeFormLayout(
    headerText: String,
    errorText: String? = null,
    logo: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    formContent: @Composable () -> Unit,
) {
    val configuration = currentDeviceConfigure()
    val headerColor = if (configuration == DeviceConfiguration.MOBILE_LANDSCAPE) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.extended.textPrimary
    }

    when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ChirpSurface(
                modifier = modifier
                    .consumeWindowInsets(WindowInsets.navigationBars)
                    .consumeWindowInsets(WindowInsets.displayCutout),
                header = {
                    Spacer(Modifier.height(32.dp))
                    logo()
                    Spacer(Modifier.height(32.dp))
                }
            ) {
                Spacer(Modifier.height(24.dp))
                AuthHeaderSection(
                    headerText = headerText,
                    headerColor = headerColor,
                    errorText = errorText
                )
                Spacer(Modifier.height(24.dp))
                formContent()
            }
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
           /* Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = modifier
            ) {
               */ Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier.fillMaxSize()
                        .consumeWindowInsets(WindowInsets.displayCutout)
                        .consumeWindowInsets(WindowInsets.navigationBars),
                )
            {
                    Column(
                        modifier = Modifier.weight(1f).padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(Modifier.height(16.dp))
                        logo()

                        AuthHeaderSection(
                            headerText = headerText,
                            headerColor = headerColor,
                            errorText = errorText,
                            headerTextAlign = TextAlign.Start
                        )


                    }
                    ChirpSurface(
                        modifier = Modifier.weight(1f).padding(10.dp)
                    ) {
                        Spacer(Modifier.height(16.dp))
                        formContent()
                    }
                }
         //   }
        }

        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Column(
                modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                logo()
                Column(
                    modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp)).background(
                            MaterialTheme.colorScheme.surface
                        ).padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AuthHeaderSection(
                        headerText = headerText,
                        headerColor = headerColor,
                        errorText = errorText
                    )
                    formContent()

                }

            }
        }
    }

}


@Composable
private fun ColumnScope.AuthHeaderSection(
    headerText: String,
    headerColor: Color,
    errorText: String? = null,
    headerTextAlign: TextAlign= TextAlign.Center
) {
    Text(
        text = headerText,
        style = MaterialTheme.typography.titleLarge,
        color = headerColor,
        textAlign =headerTextAlign,
        modifier = Modifier.fillMaxWidth()
    )

    AnimatedVisibility(errorText != null) {
        if (errorText != null) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = headerTextAlign,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}


@Preview
@Composable
private fun ChirpAdaptativeFormLayoutLightThemePreview() {
    ChirpTheme {
        ChirpAdaptativeFormLayout(
            headerText = "hello word",
            errorText = "Login faild",
            logo = { ChirpBrandLogo() },
            formContent = {
                Text(
                    text = "Simple form title",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Simple form title2",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        )
    }
}


@Preview
@Composable
private fun ChirpAdaptativeFormLayoutDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpAdaptativeFormLayout(
            headerText = "hello word",
            errorText = "Login faild",
            logo = { ChirpBrandLogo() },
            formContent = {
                Text(
                    text = "Simple form title",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Simple form title2",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        )
    }
}