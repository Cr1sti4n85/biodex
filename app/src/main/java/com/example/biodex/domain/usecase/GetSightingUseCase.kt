package com.example.biodex.domain.usecase

import com.example.biodex.domain.repository.SightingRepository
import javax.inject.Inject

class GetSightingUseCase @Inject constructor(
    private val repository: SightingRepository
) {
    operator fun invoke() = repository.getAllSights()
}