package com.example.biodex.data.repository

import com.example.biodex.data.local.dao.SightingDAO
import com.example.biodex.data.mapper.toDomain
import com.example.biodex.data.mapper.toEntity
import com.example.biodex.domain.model.Sighting
import com.example.biodex.domain.repository.SightingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SightingRepositoryImpl @Inject constructor(
    private val sightingDAO: SightingDAO
): SightingRepository {
    override fun getAllSights(): Flow<List<Sighting>> {
        return sightingDAO.getAllSightings().map {entities ->
            entities.map {it.toDomain()}
        }
    }

    override suspend fun getSightingById(id: String): Sighting? {
        return sightingDAO.getSightingById(id.toLong())?.toDomain()
    }

    override suspend fun createSighting(sighting: Sighting): Result<Boolean> {
        return try {
            sightingDAO.insertSighting(sighting.toEntity())
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadImage(uri: String): Result<String> {
        return Result.success(uri)
    }
}