package empire.digiprem.com.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import empire.digiprem.com.core.presentation.util.currentDeviceConfigure

@Composable
fun ChirpAdaptiveDialogSheetLayout(
    onDismiss:()->Unit,
    content:@Composable ()->Unit
) {
    val configuration= currentDeviceConfigure()

    if (configuration.isMobile){
        ChirpBottomSheet(
            onDismiss=onDismiss,
            content=content
        )
    }else{
        ChirpDialogContent(
            onDismiss=onDismiss,
            content=content
        )
    }
}