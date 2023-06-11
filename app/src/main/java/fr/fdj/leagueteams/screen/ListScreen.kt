package fr.fdj.leagueteams.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.fdj.leagueteams.data.viewmodel.LeagueTeamViewModel
import fr.fdj.leagueteams.ui.theme.LeagueTeamsTheme

@Composable
fun TeamListScreen(
    context: Context,
    navController: NavController,
    viewModel: LeagueTeamViewModel
) {


    LeagueTeamsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val teamListUiState by viewModel.teamList.collectAsState()
            val leagueListUiState by viewModel.leagueList.collectAsState()

            val teamList = teamListUiState.list
            val leagueList = leagueListUiState.list

            // Teams
            if (teamListUiState.isLoading) {
                Box(modifier = Modifier.padding(9.dp)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
            else if (teamListUiState.error) Text(text = "Error while fetching team data!")
            else if (teamList != null) {
                if (teamList.isNotEmpty())
                    Text(text = "First Team : ${teamList[0].strTeam.toString()}")
            }

            // League
            if (leagueListUiState.isLoading) {
                Box(modifier = Modifier.padding(9.dp)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
            else if (leagueListUiState.error) Text(text = "Error while fetching team data!")
            else if (leagueList != null) {
                if (leagueList.isNotEmpty())
                    Text(text = "\nFirst League : ${leagueList[0].strLeague.toString()}")
            }
        }
    }


}







