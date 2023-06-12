package fr.fdj.leagueteams.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    context: Context,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.TeamListScreen.route) {
        composable(route = Screen.TeamListScreen.route) {
            TeamListScreen(context, navController)
        }
    }
}