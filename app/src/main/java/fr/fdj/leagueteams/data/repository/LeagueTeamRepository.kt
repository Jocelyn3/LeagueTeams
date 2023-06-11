package fr.fdj.leagueteams.data.repository

import fr.fdj.leagueteams.data.api.LeagueListResult
import fr.fdj.leagueteams.data.api.TeamListResult
import fr.fdj.leagueteams.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LeagueTeamRepository {

    suspend fun getTeamList() : Flow<Resource<Response<TeamListResult>>>

    suspend fun getLeagueList() : Flow<Resource<Response<LeagueListResult>>>
}