package empire.digiprem.com.chirp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import chirp.composeapp.generated.resources.Res
import chirp.composeapp.generated.resources.compose_multiplatform
import empire.digiprem.com.auth.presentation.register.RegisterRoot
import empire.digiprem.com.auth.presentation.register_success.RegisterSuccessRoot
import empire.digiprem.com.auth.presentation.register_success.RegisterSuccessScreen
import empire.digiprem.com.auth.presentation.register_success.RegisterSuccessState
import empire.digiprem.com.chirp.navigation.NavigationRoot
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended

@Composable
@Preview
fun App() {
    ChirpTheme {
        NavigationRoot()
    }
}