package fr.fdj.leagueteams.data.viewmodel

import android.util.Log
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
    private val repository: LeagueTeamRepository
): ViewModel() {

    private var _teamList = MutableStateFlow(TeamListUiState(mutableListOf()))
    var teamList: StateFlow<TeamListUiState> = _teamList

    private var _leagueList = MutableStateFlow(LeagueListUiState(mutableListOf()))
    var leagueList: StateFlow<LeagueListUiState> = _leagueList


    fun updateLocalDb() {
        Log.d("", "updateLocalDb: ")
        viewModelScope.launch {
            repository.updateLocalDb()
            setLeagueList()
            setTeamList()
        }
    }

    fun getTeamList() {
        viewModelScope.launch {
            setTeamList()
        }
    }

    fun getLeagueList() {
        viewModelScope.launch {
            setLeagueList()
        }
    }

    private suspend fun setTeamList() {
        repository.getLocalTeamList().collect{
            when(it) {
                is Resource.Success -> _teamList.value = if (it.data != null) TeamListUiState(it.data) else TeamListUiState(null)
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
    private suspend fun setLeagueList() {
        repository.getLocalLeagueList().collect{
            when(it) {
                is Resource.Success -> _leagueList.value = if (it.data != null) LeagueListUiState(it.data) else LeagueListUiState(null)
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