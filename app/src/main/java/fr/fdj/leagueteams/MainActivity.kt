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
import dagger.hilt.android.AndroidEntryPoint
import fr.fdj.leagueteams.screen.Navigation
import fr.fdj.leagueteams.ui.theme.LeagueTeamsTheme
import fr.fdj.leagueteams.utils.Util

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            LeagueTeamsTheme {
                val context = this
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar { Text(text = "Weather", fontSize = 23.sp, fontWeight = FontWeight.Medium) }
                    }
                ) { contentPadding ->
                    if (Util.isNetworkAvailable(context))
                        Box(modifier = Modifier.padding(contentPadding)) {
                            Navigation(context)
                        }
                    else Text(text = "Network Unavailable")
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