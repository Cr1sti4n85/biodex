package com.example.biodex.domain.usecase

import com.example.biodex.domain.model.Sighting
import com.example.biodex.domain.repository.SightingRepository
import javax.inject.Inject

class CreateSightingUseCase @Inject constructor(
    private val repo: SightingRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        photoUri: String?
    ): Result<Boolean> {
        var finalPhotoUrl: String? = null

        if (photoUri != null) {
            val uploadResult = repo.uploadImage(photoUri)
            uploadResult.onSuccess { url ->
                finalPhotoUrl = url
            }.onFailure {
                return Result.failure(it)
            }
        }

        val newSighting = Sighting(
            title = title,
            description = description,
            photoURL = finalPhotoUrl
        )

        return repo.createSighting(newSighting)
    }
}