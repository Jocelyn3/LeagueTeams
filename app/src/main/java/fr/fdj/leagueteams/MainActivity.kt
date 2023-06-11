package fr.fdj.leagueteams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import fr.fdj.leagueteams.data.viewmodel.LeagueTeamViewModel
import fr.fdj.leagueteams.screen.Navigation
import fr.fdj.leagueteams.ui.theme.LeagueTeamsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel = hiltViewModel<LeagueTeamViewModel>()

            viewModel.getTeamList()
            viewModel.getLeagueList()

            LeagueTeamsTheme {
                val context = this
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar { Text(text = "Weather", fontSize = 23.sp, fontWeight = FontWeight.Medium) }
                    }
                ) { contentPadding ->
                    Box(modifier = Modifier.padding(contentPadding)) {
                        Navigation(context, viewModel)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LeagueTeamsTheme {

    }
}