package fr.fdj.leagueteams.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.fdj.leagueteams.data.local.dao.LeagueDao
import fr.fdj.leagueteams.data.local.dao.TeamDao
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.data.local.entity.LeagueEntity
import fr.fdj.leagueteams.utils.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [LeagueEntity::class, TeamEntity::class], version = 1, exportSchema = false)
abstract class LeagueTeamsDatabase : RoomDatabase() {

    abstract fun TeamDao(): TeamDao
    abstract fun LeagueDao(): LeagueDao

    companion object {
        @Volatile
        private var INSTANCE: LeagueTeamsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): LeagueTeamsDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LeagueTeamsDatabase::class.java,
                    Util.LEAGUE_TEAMS_DATABASE
                )
                    .addCallback(LeagueTeamsDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class LeagueTeamsDatabaseCallback(private val scope: CoroutineScope): Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {

                }
            }
        }
    }
}