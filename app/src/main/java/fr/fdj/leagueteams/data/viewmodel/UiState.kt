package fr.fdj.leagueteams.data.viewmodel

import fr.fdj.leagueteams.data.local.entity.LeagueEntity
import fr.fdj.leagueteams.data.local.entity.TeamEntity

class TeamListUiState(
    val list: MutableList<TeamEntity>?,
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)

class LeagueListUiState(
    val list: MutableList<LeagueEntity>?,
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)