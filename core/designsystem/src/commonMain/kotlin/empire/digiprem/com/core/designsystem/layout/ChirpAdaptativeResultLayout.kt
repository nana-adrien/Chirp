package empire.digiprem.com.core.designsystem.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.presentation.util.DeviceConfiguration
import empire.digiprem.com.core.presentation.util.currentDeviceConfigure
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ChirpAdaptativeResultLayout(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val configuration = currentDeviceConfigure()

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        if (configuration == DeviceConfiguration.MOBILE_PORTRAIT) {
            ChirpSurface(
                modifier = Modifier.padding(innerPadding),
                header = {
                    Spacer(Modifier.height(32.dp))
                    ChirpBrandLogo()
                    Spacer(Modifier.height(32.dp))
                },
                content = content
            )

        } else {
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
                    .background(MaterialTheme.colorScheme.background).padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {

                if (configuration != DeviceConfiguration.MOBILE_LANDSCAPE) {
                    ChirpBrandLogo()
                }
                Column(
                    modifier = Modifier.widthIn(max=480.dp).fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }

            }
        }
    }


}


@Preview
@Composable
private fun ChirpAdaptativeResultLayoutLightThemePreview() {
    ChirpTheme {
        ChirpAdaptativeResultLayout(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Register Successful !",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Preview
@Composable
private fun ChirpAdaptativeResultLayoutDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpAdaptativeResultLayout(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Register Successful !",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}