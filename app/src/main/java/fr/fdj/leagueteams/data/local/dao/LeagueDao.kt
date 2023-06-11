package fr.fdj.leagueteams.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.fdj.leagueteams.data.local.entity.LeagueEntity
import fr.fdj.leagueteams.utils.Util

@Dao
interface LeagueDao {
    @Query("SELECT * FROM ${Util.LEAGUE_TABLE}")
    suspend fun getLeagueList(): MutableList<LeagueEntity>

    @Query("SELECT * FROM ${Util.LEAGUE_TABLE} WHERE idLeague = :idLeague")
    suspend fun getLeague(idLeague: String): LeagueEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(league: LeagueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teamList: MutableList<LeagueEntity>)

    @Query("DELETE FROM ${Util.LEAGUE_TABLE}")
    suspend fun deleteAll()

    @Query("DELETE FROM ${Util.LEAGUE_TABLE} WHERE idLeague = :idLeague")
    suspend fun delete(idLeague: String)
}