package com.example.biodex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biodex.domain.model.Sighting
import com.example.biodex.domain.usecase.GetSightingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSightingUseCase: GetSightingUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchSightings()
    }

    private fun fetchSightings(){
        viewModelScope.launch {
            getSightingUseCase()
                .catch { error ->
                    _uiState.value = HomeUiState.Error(error.message ?: "Error desconocido")
                }
                .collect { sightings ->
                if(sightings.isEmpty()) {
                    _uiState.value = HomeUiState.Empty
                } else {
                    _uiState.value = HomeUiState.Success(sightings)
                }

            }
        }
    }
    sealed class HomeUiState {
        object Loading : HomeUiState()
        object Empty : HomeUiState()
        data class Success(val sightings: List<Sighting>): HomeUiState()
        data class Error(val message: String): HomeUiState()
    }
}