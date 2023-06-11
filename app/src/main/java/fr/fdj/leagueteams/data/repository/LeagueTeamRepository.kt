package fr.fdj.leagueteams.data.repository

import fr.fdj.leagueteams.data.local.entity.LeagueEntity
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LeagueTeamRepository {
    suspend fun updateLocalDb()
    suspend fun getLocalTeamList() : Flow<Resource<MutableList<TeamEntity>>>
    suspend fun getLocalLeagueList() : Flow<Resource<MutableList<LeagueEntity>>>
}