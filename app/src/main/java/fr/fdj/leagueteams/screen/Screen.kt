package fr.fdj.leagueteams.screen

sealed class Screen(val route: String) {
    object SearchScreen: Screen("search_screen")
    object TeamListScreen: Screen("team_list_screen")

}
