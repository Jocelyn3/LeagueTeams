package fr.fdj.leagueteams.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.fdj.leagueteams.utils.Util
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Util.TEAM_TABLE)
data class TeamEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "idTeam") val idTeam: String,

    @ColumnInfo("strTeam") val strTeam: String?,
    @ColumnInfo("idLeague") val idLeague: String?,
    @ColumnInfo("strSport") val strSport: String?,
    @ColumnInfo("strLeague") val strLeague: String?,
    @ColumnInfo("strTeamBadge") val strTeamBadge: String?
) : Parcelable {
    fun matches(query: String): Boolean {
        return strLeague?.contains(query, ignoreCase = true) ?: false
    }
}
