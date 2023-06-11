package fr.fdj.leagueteams.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.fdj.leagueteams.data.viewmodel.LeagueTeamViewModel

@Composable
fun Navigation(
    context: Context,
    viewModel: LeagueTeamViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.TeamListScreen.route) {
        composable(route = Screen.TeamListScreen.route) {
            TeamListScreen(context, navController, viewModel)
        }
    }
}