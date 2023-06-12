package fr.fdj.leagueteams.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Util {
    const val SOCCER = "Soccer"
    const val TEAM_TABLE = "team_table"
    const val LEAGUE_TABLE = "league_table"
    const val BASE_URL = "https://www.thesportsdb.com/api/"
    const val LEAGUE_TEAMS_DATABASE = "league_teams_database"
    const val LEAGUES_ENDPOINT = "v1/json/50130162/all_leagues.php"
    const val TEAMS_ENDPOINT = "v1/json/50130162/search_all_teams.php"
//    const val TEAMS_ENDPOINT = "v1/json/50130162/search_all_teams.php?l=French%20Ligue%201"


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}