package com.example.biodex.data.repository

import com.example.biodex.data.local.dao.SightingDAO
import com.example.biodex.data.mapper.toDomain
import com.example.biodex.data.mapper.toDto
import com.example.biodex.data.mapper.toEntity
import com.example.biodex.data.remote.source.SightingRemoteDataSource
import com.example.biodex.domain.model.Sighting
import com.example.biodex.domain.repository.SightingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class SightingRepositoryImpl @Inject constructor(
    private val sightingDAO: SightingDAO,
    private val remoteDataSource: SightingRemoteDataSource
): SightingRepository {
    override fun getAllSights(): Flow<List<Sighting>> {
        return sightingDAO.getAllSightings().map {entities ->
            entities.map {it.toDomain()}
        }
            .onStart { syncSightings() }
    }

    private suspend fun syncSightings(){
        val remoteRes = remoteDataSource.fetchSightings()
        remoteRes.onSuccess { dtos ->
            val entities = dtos.map {
                it.toDomain().toEntity()
            }
            entities.forEach { sightingDAO.insertSighting(it) }
        }.onFailure {
            error -> throw error
        }
//        try {
//            val remoteRes = remoteDataSource.fetchSightings()
//            remoteRes.onSuccess { dtos ->
//                val entities = dtos.map {
//                    it.toDomain().toEntity()
//                }
//                entities.forEach { sightingDAO.insertSighting(it) }
//            }.onFailure {
//                Timber.d("Error en l sincronización: ${it.message}")
//            }
//        } catch (e: Exception) {
//            Timber.d("Ha ocurrido un error: ${e.message}")
//            }

    }

    override suspend fun getSightingById(id: String): Sighting? {
        return sightingDAO.getSightingById(id.toLong())?.toDomain()
    }

    override suspend fun createSighting(sighting: Sighting): Result<Boolean> {
        return try {
            val remoteRes = remoteDataSource.submitSighting(sighting.toDto())

            if (remoteRes.isSuccess) {
                val savedSighting = remoteRes.getOrNull()
                savedSighting?.let {
                    sightingDAO.insertSighting(it.toDomain().toEntity())
                }
                Result.success(true)
            } else {
                sightingDAO.insertSighting(sighting.toEntity())
                Result.failure(Exception("El registro se ha guardado localmente"))
            }
//            sightingDAO.insertSighting(sighting.toEntity())
//            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadImage(uri: String): Result<String> {
        return Result.success(uri)
    }
}