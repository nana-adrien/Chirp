package empire.digiprem.com.core.designsystem.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.logo_chirp
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
 fun ChirpBrandLogo(
    modifier: Modifier= Modifier,) {
    Icon(
        imageVector = vectorResource(Res.drawable.logo_chirp),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier =modifier
    )

}



@Preview
@Composable
private fun ChirpBrandLogoLightThemePreview() {
    ChirpTheme{
       ChirpBrandLogo()
       }
}


@Preview
@Composable
private fun ChirpBrandLogoDarkThemePreview() {
    ChirpTheme(
           darkTheme = true
       ){
       ChirpBrandLogo()
       }
}