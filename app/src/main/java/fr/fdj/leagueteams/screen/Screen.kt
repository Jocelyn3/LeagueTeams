package fr.fdj.leagueteams.screen

sealed class Screen(val route: String) {
    object TeamListScreen: Screen("team_list_screen")

}
