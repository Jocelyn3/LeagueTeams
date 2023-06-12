package fr.fdj.leagueteams.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.data.viewmodel.LeagueTeamViewModel
import fr.fdj.leagueteams.ui.theme.LeagueTeamsTheme

@Composable
fun TeamListScreen(
    context: Context,
    navController: NavController,
) {

    val viewModel = hiltViewModel<LeagueTeamViewModel>()
    viewModel.updateLocalDb()

    LeagueTeamsTheme {
        val isUpdatingState by viewModel.isUpdating.collectAsState()
        val searchTextState by viewModel.searchText.collectAsState()
        val searchTeamListState by viewModel.searchTeamList.collectAsState()


        val list = searchTeamListState.list
        val searchTeamList = list?.subList(0, list.size/2)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(11.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = searchTextState,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                placeholder = { Text(text = "Recherche")}
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Teams
            if (isUpdatingState || searchTeamListState.isLoading) {
                Box(modifier = Modifier.align(CenterHorizontally).padding(9.dp)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }
            }
            else if (searchTeamListState.error) Text(text = "Error while fetching team data! : ${searchTeamListState.errorMessage}")
            else if (searchTeamList != null) {
                if (searchTeamList.isNotEmpty())
                    TeamList(list = searchTeamList)
                else
                    Text(text = "No team found!!!")

            }

        }
    }
    
}

@Composable
fun TeamRow(team: TeamEntity) {
    Surface {
        Text(text = team.strTeam.toString())
    }
}

@Composable
fun TeamList(
    list: List<TeamEntity>?
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        if (list != null && list.isNotEmpty()) {
            items(list) { item ->
                TeamRow(team = item)
            }
        }

    }
}







