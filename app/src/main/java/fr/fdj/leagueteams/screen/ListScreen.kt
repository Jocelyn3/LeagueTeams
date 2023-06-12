package fr.fdj.leagueteams.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import fr.fdj.leagueteams.R
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
        ) {
            TextField(
                value = searchTextState,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                placeholder = { Text(text = "Recherche")},
                trailingIcon = { Icon(Icons.Filled.Clear, "", tint = Blue, modifier = Modifier.clickable { viewModel.clearText() })}
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Teams
            if (isUpdatingState || searchTeamListState.isLoading) {
                Box(modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(9.dp)) {
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
        AsyncImage(
            model = team.strTeamBadge,
            contentDescription = "Team badge",
            modifier = Modifier.padding(7.dp),
            placeholder = painterResource(id = R.drawable.baseline_refresh_24)
        )
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







