package com.example.biodex.ui.viewmodel.state

import android.net.Uri

data class CreateSightingUiState(
    val isLoading: Boolean = false,
    val titleError: Int? = null,
    val descriptionError: Int? = null,
    val photoUri: Uri? = null,
    val isFormValid: Boolean = false
) {
}