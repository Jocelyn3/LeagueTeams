package fr.fdj.leagueteams.data.api

import android.util.Log
import fr.fdj.leagueteams.data.local.dao.LeagueDao
import fr.fdj.leagueteams.data.local.dao.TeamDao
import fr.fdj.leagueteams.data.local.entity.LeagueEntity
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.data.repository.LeagueTeamRepository
import fr.fdj.leagueteams.utils.Resource
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
        if (localLeagueList.isEmpty())
            updateLocalLeagueList(true)
        else
            updateLocalLeagueList(false, localLeagueList)

        val localTeamList = teamDao.getTeamList()
        if (localTeamList.isEmpty())
            updateLocalTeamList(true)
        else
            updateLocalTeamList(false, localTeamList)
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


    private suspend fun updateLocalLeagueList(
        addAllRemoteList: Boolean,
        localList: MutableList<LeagueEntity> = arrayListOf()
    ) {
        getRemoteLeagueList().collect{
            when(it) {
                is Resource.Success -> {
                    val tempRemoteLeagueList = it.data?.body()?.leagueList
                    val remoteLeagueEntityList = arrayListOf<LeagueEntity>()
                    tempRemoteLeagueList?.forEach { league ->
                        remoteLeagueEntityList.add(
                            LeagueEntity(
                                idLeague = league.idLeague,
                                strLeague = league.strLeague,
                                strLeagueAlternate = league.strLeagueAlternate
                            )
                        )
                    }

                    if (remoteLeagueEntityList.isNotEmpty()) {
                        if (addAllRemoteList)
                            leagueDao.insertAll(remoteLeagueEntityList)
                        else {
                            localList.forEach { league ->
                                if (!remoteLeagueEntityList.contains(league))
                                    league.idLeague.let { idLeague -> leagueDao.delete(idLeague) }
                            }
                            remoteLeagueEntityList.forEach { league ->
                                run {
                                    if (!localList.contains(league))
                                        leagueDao.insert(league)
                                }
                            }
                        }
                    }

                }
                is Resource.Loading -> Log.d(TAG, "saveLeagueListInLocalDb: Saving league list in local Db........")
                is Resource.Failure -> Log.e(TAG, "saveLeagueListInLocalDb: Fail saving league list in local Db. Error message = " + it.e?.message)
            }
        }

    }

    private suspend fun updateLocalTeamList(
        addAllRemoteList: Boolean,
        localList: MutableList<TeamEntity> = arrayListOf()
    ) {
        getRemoteTeamList().collect{
            when(it) {
                is Resource.Success -> {
                    val tempRemoteTeamList = it.data?.body()?.teamList
                    val remoteTeamList = arrayListOf<TeamEntity>()
                    tempRemoteTeamList?.forEach { team -> run {
                        remoteTeamList.add(
                            TeamEntity(
                                idTeam = team.idTeam,
                                strTeam = team.strTeam,
                                idLeague = team.idLeague,
                                strLeague = team.strLeague,
                                strTeamBadge = team.strTeamBadge
                            )
                        )
                    } }

                    if (remoteTeamList.isNotEmpty()) {
                        if (addAllRemoteList)
                            teamDao.insertAll(remoteTeamList)
                        else {
                            localList.forEach { team ->
                                if (!remoteTeamList.contains(team))
                                    team.idLeague?.let { idTeam -> leagueDao.delete(idTeam) }
                            }
                            remoteTeamList.forEach { team ->
                                run {
                                    if (!localList.contains(team))
                                        teamDao.insert(team)
                                }
                            }
                        }
                    }

                }
                is Resource.Loading -> Log.d(TAG, "saveLeagueListInLocalDb: Saving team list in local Db........")
                is Resource.Failure -> Log.e(TAG, "saveLeagueListInLocalDb: Fail saving team list in local Db. Error message = " + it.e?.message)
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