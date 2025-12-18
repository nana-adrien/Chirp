package empire.digiprem.com.core.designsystem.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeResultLayout
import empire.digiprem.com.core.designsystem.theme.ChirpTheme

@PreviewScreenSizes
@PreviewLightDark
@Preview(
    device = Devices.NEXUS_10
)
@Composable
fun ChirpAdaptativeResultLayoutPreview(){
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