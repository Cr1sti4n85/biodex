package com.example.biodex.domain.repository

import com.example.biodex.domain.model.Sighting
import kotlinx.coroutines.flow.Flow

interface SightingRepository {

    fun getAllSights(): Flow<List<Sighting>>

    suspend fun getSightingById(id: String): Sighting?

    suspend fun createSighting(sighting: Sighting): Result<Boolean>

    suspend fun uploadImage(uri: String): Result<String>
}