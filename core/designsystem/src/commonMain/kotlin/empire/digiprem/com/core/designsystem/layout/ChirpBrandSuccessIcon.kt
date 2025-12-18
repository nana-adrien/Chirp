package empire.digiprem.com.core.designsystem.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.logo_chirp
import chirp.core.designsystem.generated.resources.success_checkmark
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
 fun ChirpBrandSuccessIcon(
    modifier: Modifier= Modifier,) {
    Icon(
        imageVector = vectorResource(Res.drawable.success_checkmark),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.extended.success,
        modifier =modifier
    )

}



@Preview
@Composable
private fun ChirpBrandSuccessIconLightThemePreview() {
    ChirpTheme{
       ChirpBrandLogo()
       }
}


@Preview
@Composable
private fun ChirpBrandSuccessIconDarkThemePreview() {
    ChirpTheme(
           darkTheme = true
       ){
       ChirpBrandLogo()
       }
}