package fr.fdj.leagueteams.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.fdj.leagueteams.data.local.entity.TeamEntity
import fr.fdj.leagueteams.data.repository.LeagueTeamRepository
import fr.fdj.leagueteams.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTeamViewModel @Inject constructor(
    private val repository: LeagueTeamRepository
): ViewModel() {

    private val _teamList = MutableStateFlow(TeamListUiState(mutableListOf()))
    val teamList: StateFlow<TeamListUiState> = _teamList

    private val _leagueList = MutableStateFlow(LeagueListUiState(mutableListOf()))
    val leagueList: StateFlow<LeagueListUiState> = _leagueList

    private val _searchTeamList = MutableStateFlow(TeamListUiState(_teamList.value.list))
    val searchTeamList: StateFlow<TeamListUiState> = _searchTeamList

    private val _searchLeagueList = MutableStateFlow(LeagueListUiState(_leagueList.value.list))
    val searchLeagueList: StateFlow<LeagueListUiState> = _searchLeagueList

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating = _isUpdating.asStateFlow()



    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchTeamList.value = _teamList.value

        _searchText.value = text
        var teams: List<TeamEntity>? = _teamList.value.list

        if (text.isNotEmpty() && text.length >= 4) {
            teams = teams?.filter { it.matches(text) }
            _searchTeamList.value = TeamListUiState(teams?.toMutableList())
        }
    }

    fun updateLocalDb() {
        Log.d("", "updateLocalDb: ")
        viewModelScope.launch {
            _isUpdating.value = true
            repository.updateLocalDb()
            setLeagueList()
            setTeamList()
            _isUpdating.value = false
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

            _searchTeamList.value = _teamList.value
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

            _searchLeagueList.value = _leagueList.value
        }
    }
}