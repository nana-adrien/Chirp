package empire.digiprem.com.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import empire.digiprem.com.auth.presentation.navigation.AuthGraphRoutes
import empire.digiprem.com.auth.presentation.navigation.authGraph
import empire.digiprem.com.chat.presentation.navigation.ChatGraphRoutes
import empire.digiprem.com.chat.presentation.navigation.chatGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination:Any,
    modifier: Modifier =Modifier,
) {

    NavHost(
        modifier=modifier,
        navController=navController,
        startDestination = startDestination
    ){
        authGraph(
            navController=navController,
            onLoginSuccess = {
                navController.navigate(ChatGraphRoutes.Graph){
                    popUpTo( AuthGraphRoutes.Graph){
                        inclusive=true
                    }
                }
            }
        )
        chatGraph(
            navController=navController
        )
    }
}

