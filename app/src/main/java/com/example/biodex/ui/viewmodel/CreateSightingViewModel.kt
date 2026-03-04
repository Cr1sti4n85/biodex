package com.example.biodex.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biodex.R
import com.example.biodex.core.di.SightingFileManager
import com.example.biodex.core.di.IoDispatcher
import com.example.biodex.core.utils.ImageProcessor
import com.example.biodex.domain.usecase.CreateSightingUseCase
import com.example.biodex.domain.usecase.ValidateSightingUseCase
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateSightingViewModel @Inject constructor(
    private val validateSightingUseCase: ValidateSightingUseCase,
    private val createSightingUseCase: CreateSightingUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val sightingFileManager: SightingFileManager,
    private val imageProcessor: ImageProcessor,
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateSightingUiState())
    val uiState: StateFlow<CreateSightingUiState> = _uiState.asStateFlow()


    private val _uiEvent = Channel<CreateSightingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentTitle = ""
    private var currentDescription = ""

    fun onLocationCaptured(lat: Double, lng: Double) {
        _uiState.update { state ->
            state.copy(
                latitude = lat,
                longitude = lng
            )
        }
        validateForm()
    }

    fun onAddressUpdate(address: String) {
        _uiState.update { state ->
            state.copy(address = address)
        }
    }

    fun onTitleChanged(text: String) {
        currentTitle = text
        val result = validateSightingUseCase.execute(currentTitle, currentDescription)
    }

    fun onDescriptionChanged(text: String) {
        currentDescription = text
        validateForm()
    }

    fun onPhotoSelected(context: Context, uri: Uri) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }

            val interalFile = sightingFileManager.createInternalFile()
            val optimizedPath = imageProcessor.process(context, uri, interalFile)

            _uiState.update { state ->
                state.copy(
                    photoUri = optimizedPath?.let { Uri.fromFile(File(it)) },
                    isLoading = false
                )
            }
            validateForm()
        }
    }

    private fun validateForm() {
        val isTitleValid = currentTitle.length >= 5
        val isDescValid = currentDescription.length >= 10
        val isPhotoValid = _uiState.value.photoUri != null
        val isLocationValid = _uiState.value.latitude != null && _uiState.value.longitude != null

        _uiState.update { state ->
            state.copy(
                titleError = if (currentTitle.isNotEmpty() && !isTitleValid) R.string.error_title_short else null,
                isFormValid = isTitleValid && isDescValid && isPhotoValid && isLocationValid
            )
        }
    }

    fun submit() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }

            delay(3000)

            val result = createSightingUseCase(
                title = currentTitle,
                description = currentDescription,
                photoUri = _uiState.value.photoUri?.toString()
            )

            result.onSuccess {
                _uiEvent.send(CreateSightingUiEvent.SuccessNavigation)
            }.onFailure{ error ->
                _uiEvent.send(CreateSightingUiEvent.ShowError(error.message ?: "Error en la operación"))
            }

            _uiState.update { it.copy(isLoading = false) }

        }
    }

}