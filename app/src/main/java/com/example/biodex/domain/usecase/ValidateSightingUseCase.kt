package com.example.biodex.domain.usecase

import javax.inject.Inject

class ValidateSightingUseCase @Inject constructor() {

    fun execute(title: String, description: String) : ValidationResult {
        if (title.isBlank()) {
            return ValidationResult.Error("El nombre no puede estar vacío")
        }
        if (title.length < 3) {
            return ValidationResult.Error("El nombre es muy corto")
        }
        if (description.isBlank()) {
            return ValidationResult.Error("La descripción es obligatoria")
        }
        if (description.length < 10) {
            return ValidationResult.Error("La descripción debe tener un mínimo de 10 carateres")
        }
        return ValidationResult.Success
    }

    sealed class ValidationResult {
        data object Success: ValidationResult()
        data class Error(val message: String) : ValidationResult()
    }
}