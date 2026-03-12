package empire.digiprem.com.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import empire.digiprem.com.chat.presentation.chat_list_detail.ChatListDetailAdaptiveLayout
import empire.digiprem.com.chat.presentation.chat_list_detail.ChatListDetailViewModel
import kotlinx.serialization.Serializable

sealed interface ChatGraphRoutes {

    @Serializable
    data object Graph:ChatGraphRoutes

    @Serializable
    data object ChatListDetailRoute:ChatGraphRoutes

}

fun NavGraphBuilder.chatGraph(
    navController:NavController
){
    navigation<ChatGraphRoutes.Graph>(
        startDestination = ChatGraphRoutes.ChatListDetailRoute
    ){
        composable<ChatGraphRoutes.ChatListDetailRoute> {
            ChatListDetailAdaptiveLayout(
                onLogout = {}
            )
        }
    }
}