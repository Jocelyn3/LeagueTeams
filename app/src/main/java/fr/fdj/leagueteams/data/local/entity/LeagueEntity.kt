package fr.fdj.leagueteams.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.fdj.leagueteams.utils.Util
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Util.LEAGUE_TABLE)
data class LeagueEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "idLeague") val idLeague: String,

    @ColumnInfo("strLeague") val strLeague: String?,
    @ColumnInfo("strLeagueAlternate") val strLeagueAlternate: String?
) : Parcelable
