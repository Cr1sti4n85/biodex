package com.example.biodex.ui.viewmodel.state

sealed interface CreateSightingUiEvent {

    data class ShowError(val message: String) : CreateSightingUiEvent
    data object SuccessNavigation : CreateSightingUiEvent
}