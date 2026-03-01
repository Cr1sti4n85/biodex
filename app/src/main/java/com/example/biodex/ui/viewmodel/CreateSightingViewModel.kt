package com.example.biodex.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biodex.R
import com.example.biodex.core.di.IoDispatcher
import com.example.biodex.ui.viewmodel.state.CreateSightingUiEvent
import com.example.biodex.ui.viewmodel.state.CreateSightingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSightingViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateSightingUiState())
    val uiState: StateFlow<CreateSightingUiState> = _uiState.asStateFlow()


    private val _uiEvent = Channel<CreateSightingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentTitle = ""
    private var currentDescription = ""

    fun onTitleChanged(text: String) {
        currentTitle = text
        validateForm()
    }

    fun onDescriptionChanged(text: String) {
        currentDescription = text
        validateForm()
    }

    fun onPhotoSelected(uri: Uri) {
        _uiState.update { it.copy(photoUri = uri) }
        validateForm()
    }

    private fun validateForm() {
        val isTitleValid = currentTitle.length >= 5
        val isDescValid = currentDescription.length >= 10

        _uiState.update { state ->
            state.copy(
                titleError = if (currentTitle.isNotEmpty() && !isTitleValid) R.string.error_title_short else null,
                isFormValid = isTitleValid && isDescValid
            )
        }
    }

    fun submit() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }

            delay(3000)

            val success = true

            if (success){
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.send(CreateSightingUiEvent.SuccessNavigation)
            } else {
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.send(CreateSightingUiEvent.ShowError("Error en la operación"))
            }

        }
    }








}