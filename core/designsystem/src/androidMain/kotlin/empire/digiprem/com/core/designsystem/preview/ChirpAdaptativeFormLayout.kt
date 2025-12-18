package empire.digiprem.com.core.designsystem.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeFormLayout
import empire.digiprem.com.core.designsystem.layout.ChirpBrandLogo
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
@PreviewScreenSizes
@PreviewLightDark
@Preview(
    device = Devices.NEXUS_10
)
@Composable
fun ChirpAdaptativeFormLayoutPreviews(){
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