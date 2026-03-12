package empire.digiprem.com.chirp

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

import empire.digiprem.com.auth.presentation.navigation.AuthGraphRoutes
import empire.digiprem.com.chat.presentation.navigation.ChatGraphRoutes
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
                startDestination= if (state.isLoggedIn) ChatGraphRoutes.Graph  else  AuthGraphRoutes.Graph
            )
        }
    }
}