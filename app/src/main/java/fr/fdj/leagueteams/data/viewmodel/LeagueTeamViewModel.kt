package fr.fdj.leagueteams.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.fdj.leagueteams.data.repository.LeagueTeamRepository
import fr.fdj.leagueteams.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTeamViewModel @Inject constructor(
    private val leagueTeamRepository: LeagueTeamRepository
): ViewModel() {

    private var _teamList = MutableStateFlow(TeamListUiState(mutableListOf()))
    var teamList: StateFlow<TeamListUiState> = _teamList

    private var _leagueList = MutableStateFlow(LeagueListUiState(mutableListOf()))
    var leagueList: StateFlow<LeagueListUiState> = _leagueList


    fun getTeamList() {
        viewModelScope.launch {
            leagueTeamRepository.getTeamList().collect{
                when(it) {
                    is Resource.Success -> _teamList.value = if (it.data != null) TeamListUiState(it.data.body()?.teamList) else TeamListUiState(null)
                    is Resource.Loading -> _teamList.value = TeamListUiState(
                        list = null,
                        isLoading = true
                    )
                    is Resource.Failure -> {
                        _teamList.value = TeamListUiState(
                            list = null,
                            error = true,
                            errorMessage = it.e?.message
                        )
                    }
                }
            }
        }
    }


    fun getLeagueList() {
        viewModelScope.launch {
            leagueTeamRepository.getLeagueList().collect{
                when(it) {
                    is Resource.Success -> _leagueList.value = if (it.data != null) LeagueListUiState(it.data.body()?.leagueList) else LeagueListUiState(null)
                    is Resource.Loading -> _leagueList.value = LeagueListUiState(
                        list = null,
                        isLoading = true
                    )
                    is Resource.Failure -> {
                        _leagueList.value = LeagueListUiState(
                            list = null,
                            error = true,
                            errorMessage = it.e?.message
                        )
                    }
                }
            }
        }
    }
}