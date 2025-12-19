package empire.digiprem.com.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import empire.digiprem.com.core.designsystem.theme.ChirpTheme

@Composable
fun RegisterRoot(viewModel: RegisterViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreen(state: RegisterState, onAction: (RegisterAction) -> Unit) {

}



@Composable
private fun RegisterPreview() {
    RegisterScreen(
        state = RegisterState(),
        onAction = {

        }
    )
}

@Composable
private fun RegisterLightThemePreview() {
    ChirpTheme {
        RegisterPreview()
    }
}

@Composable
private fun RegisterDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        RegisterPreview()
    }
}