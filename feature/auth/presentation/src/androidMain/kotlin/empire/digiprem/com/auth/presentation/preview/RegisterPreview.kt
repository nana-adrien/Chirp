package empire.digiprem.com.auth.presentation.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Devices.PIXEL_3A
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import empire.digiprem.com.auth.presentation.register.RegisterScreenPreview
import empire.digiprem.com.core.designsystem.theme.ChirpTheme

@PreviewScreenSizes
@PreviewLightDark
@Preview(
    device = PIXEL_3A
)
@Composable
fun RegisterPreview() {
    ChirpTheme {
        RegisterScreenPreview()
    }
}