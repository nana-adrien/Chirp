package empire.digiprem.com.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import empire.digiprem.com.auth.presentation.navigation.AuthGraphRoutes
import empire.digiprem.com.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    modifier: Modifier =Modifier,
) {

    NavHost(
        modifier=modifier,
        navController=navController,
        startDestination = AuthGraphRoutes.Graph
    ){
        authGraph(
            navController=navController,
            onLoginSuccess = {

            }
        )
    }
}

