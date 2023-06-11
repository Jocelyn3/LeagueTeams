package fr.fdj.leagueteams.data.api

import fr.fdj.leagueteams.data.repository.LeagueTeamRepository
import fr.fdj.leagueteams.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class LeagueTeamRepoImpl @Inject constructor(
    private val leagueTeamApi: LeagueTeamApi
): LeagueTeamRepository {

    override suspend fun getTeamList() = flow {
        try {
            emit(Resource.Loading)
            val result = leagueTeamApi.getTeams()
            emit(Resource.Success(result))
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getLeagueList() = flow {
        try {
            emit(Resource.Loading)
            val result = leagueTeamApi.getLeague()
            emit(Resource.Success(result))
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

}