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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import chirp.composeapp.generated.resources.Res
import chirp.composeapp.generated.resources.compose_multiplatform
import empire.digiprem.com.auth.presentation.navigation.AuthGraphRoutes
import empire.digiprem.com.chat.presentation.chat_detail.ChatDetail
import empire.digiprem.com.chirp.navigation.DeepLinkListener
import empire.digiprem.com.chirp.navigation.NavigationRoot
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    onAuthenticationChecked:()->Unit ={},
    viewModel: MainViewModel= koinViewModel()
) {
    val navController= rememberNavController()
    DeepLinkListener(navController)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingAuth){
        if (!state.isCheckingAuth){
            onAuthenticationChecked()
        }
    }
    ChirpTheme {
        if (!state.isCheckingAuth){
            NavigationRoot(
                navController=  navController,
                startDestination= if (state.isLoggedIn) ChatDetail  else  AuthGraphRoutes.Graph
            )
        }
    }
}