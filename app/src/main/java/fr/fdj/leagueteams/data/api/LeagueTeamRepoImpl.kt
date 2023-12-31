package fr.fdj.leagueteams.data.api

import android.util.Log
import fr.fdj.leagueteams.data.local.dao.LeagueDao
import fr.fdj.leagueteams.data.local.dao.TeamDao
import fr.fdj.leagueteams.data.local.entity.LeagueEntity
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.data.repository.LeagueTeamRepository
import fr.fdj.leagueteams.utils.Resource
import fr.fdj.leagueteams.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "LeagueTeamRepoImpl"

class LeagueTeamRepoImpl @Inject constructor(
    private val leagueTeamApi: LeagueTeamApi,
    private val teamDao: TeamDao,
    private val leagueDao: LeagueDao
): LeagueTeamRepository {

    override suspend fun updateLocalDb() {
        val localLeagueList = leagueDao.getLeagueList()
        if (localLeagueList.isEmpty()) {
            updateLocalLeagueList()
        }

        val localTeamList = teamDao.getTeamList()
        if (localTeamList.isEmpty())
            updateLocalTeamList()
    }



    override suspend fun getLocalTeamList() = flow {
        try {
            emit(Resource.Loading)
            val list = teamDao.getTeamList()
            emit(Resource.Success(list))
        }catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
    override suspend fun getLocalLeagueList() = flow {
        try {
            emit(Resource.Loading)
            val list = leagueDao.getLeagueList()
            emit(Resource.Success(list))
        }catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRemoteTeamList(strLeague: String) = flow {
        try {
            emit(Resource.Loading)
            var finalStrLeague = strLeague
            val last = strLeague[strLeague.lastIndex]
            if (last == ' ')
                finalStrLeague = strLeague.substring(0, strLeague.lastIndex)
            finalStrLeague = finalStrLeague.replace(" ", "_")

            val result = leagueTeamApi.getTeams(finalStrLeague)
            Log.d(TAG, "getRemoteTeamList: ${result.raw().request.url}")
            emit(Resource.Success(result))
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun updateLocalLeagueList() {
        getRemoteLeagueList().collect{
            when(it) {
                is Resource.Success -> {
                    var tempRemoteLeagueList = it.data?.body()?.leagueList
                    tempRemoteLeagueList = tempRemoteLeagueList?.filter { league ->  league.strSport.equals(Util.SOCCER) }?.toMutableList()
                    val remoteLeagueEntityList = arrayListOf<LeagueEntity>()
                    tempRemoteLeagueList?.forEach { league ->
                        remoteLeagueEntityList.add(
                            LeagueEntity(
                                idLeague = league.idLeague,
                                strSport = league.strSport,
                                strLeague = league.strLeague,
                                strLeagueAlternate = league.strLeagueAlternate
                            )
                        )
                    }

                    leagueDao.insertAll(remoteLeagueEntityList)

                }
                is Resource.Loading -> Log.d(TAG, "saveLeagueListInLocalDb: Saving team list in local Db........")
                is Resource.Failure -> Log.e(TAG, "saveLeagueListInLocalDb: Fail saving team list in local Db. Error message = " + it.e?.message)
            }
        }

    }

    private suspend fun updateLocalTeamList(
    ) {
        val localLeagueList = leagueDao.getLeagueList()
        localLeagueList.forEach { league ->
            getRemoteTeamList(league.strLeague.toString()).collect{
                when(it) {
                    is Resource.Success -> {
                        val tempRemoteTeamList = it.data?.body()?.teamList
                        val remoteTeamEntityList = arrayListOf<TeamEntity>()
                        tempRemoteTeamList?.forEach { team ->
                            remoteTeamEntityList.add(
                                TeamEntity(
                                    idTeam = team.idTeam,
                                    strTeam = team.strTeam,
                                    strSport = team.strSport,
                                    idLeague = team.idLeague,
                                    strLeague = team.strLeague,
                                    strTeamBadge = team.strTeamBadge
                                )
                            )
                        }

                        teamDao.insertAll(remoteTeamEntityList)


                    }
                    is Resource.Loading -> Log.d(TAG, "saveLeagueListInLocalDb: Saving team list in local Db........")
                    is Resource.Failure -> Log.e(TAG, "saveLeagueListInLocalDb: Fail saving team list in local Db. Error message = " + it.e?.message)
                }
            }
        }
    }

    private suspend fun getRemoteTeamList() = flow {
        try {
            emit(Resource.Loading)
            val result = leagueTeamApi.getTeams()
            emit(Resource.Success(result))
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getRemoteLeagueList() = flow {
        try {
            emit(Resource.Loading)
            val result = leagueTeamApi.getLeague()
            emit(Resource.Success(result))
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)


}