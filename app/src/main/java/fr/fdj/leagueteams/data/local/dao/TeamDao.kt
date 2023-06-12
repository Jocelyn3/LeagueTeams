package fr.fdj.leagueteams.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.utils.Util

@Dao
interface TeamDao {
    @Query("SELECT * FROM ${Util.TEAM_TABLE}")
    suspend fun getTeamList(): MutableList<TeamEntity>

    @Query("SELECT * FROM ${Util.TEAM_TABLE} WHERE idTeam = :idTeam")
    suspend fun getTeam(idTeam: String): TeamEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teamList: MutableList<TeamEntity>)

    @Query("DELETE FROM ${Util.TEAM_TABLE}")
    suspend fun deleteAll()

    @Query("DELETE FROM ${Util.TEAM_TABLE} WHERE idTeam = :idTeam")
    suspend fun delete(idTeam: String)
}