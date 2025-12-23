package empire.digiprem.com.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import empire.digiprem.com.auth.presentation.navigation.AuthGraphRoutes
import empire.digiprem.com.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot(
    modifier:Modifier=Modifier
) {
    val navController= rememberNavController()

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