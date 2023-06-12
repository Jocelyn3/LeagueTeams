package fr.fdj.leagueteams.data.api

import com.google.gson.annotations.SerializedName
import fr.fdj.leagueteams.model.League
import fr.fdj.leagueteams.model.Team
import fr.fdj.leagueteams.utils.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeagueTeamApi {
    @GET(Util.LEAGUES_ENDPOINT)
    suspend fun getLeague(): Response<LeagueListResult>

    @GET(Util.TEAMS_ENDPOINT)
    suspend fun getTeams(): Response<TeamListResult>

    @GET(Util.TEAMS_ENDPOINT)
    suspend fun getTeams(@Query("l") strLeague: String): Response<TeamListResult>
}

data class TeamListResult(@SerializedName("teams") val teamList: MutableList<Team>)

data class LeagueListResult(@SerializedName("leagues") val leagueList: MutableList<League>)

