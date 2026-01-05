package empire.digiprem.com.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import empire.digiprem.com.auth.presentation.navigation.AuthGraphRoutes
import empire.digiprem.com.auth.presentation.navigation.authGraph
import empire.digiprem.com.chat.presentation.chat_detail.ChatDetail
import empire.digiprem.com.chat.presentation.chat_detail.ChatDetailRoot

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
                navController.navigate(ChatDetail){
                    popUpTo( AuthGraphRoutes.Graph){
                        inclusive=true
                    }
                }
            }
        )
        composable<ChatDetail>{
            ChatDetailRoot()
        }
    }
}

