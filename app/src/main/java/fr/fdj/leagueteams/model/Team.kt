package fr.fdj.leagueteams.model

data class Team(
    val idTeam: String,
    val strTeam: String? = null,
    val idLeague: String? = null,
    val strLeague: String? = null,
    val strTeamBadge: String? = null
)