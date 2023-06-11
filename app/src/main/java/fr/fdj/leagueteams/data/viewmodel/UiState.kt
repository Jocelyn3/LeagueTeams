package fr.fdj.leagueteams.data.viewmodel

import fr.fdj.leagueteams.model.League
import fr.fdj.leagueteams.model.Team

class TeamListUiState(
    val list: MutableList<Team>?,
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)

class LeagueListUiState(
    val list: MutableList<League>?,
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)