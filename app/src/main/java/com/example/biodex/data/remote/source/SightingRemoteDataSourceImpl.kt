package com.example.biodex.data.remote.source

import com.example.biodex.data.remote.api.BioDexApiService
import com.example.biodex.data.remote.dto.SightingDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SightingRemoteDataSourceImpl @Inject constructor(
    private val bioDexApiService: BioDexApiService
): SightingRemoteDataSource {
    override suspend fun fetchSightings(): Result<List<SightingDto>> = withContext(Dispatchers.IO) {
        try {
            val res = bioDexApiService.getSightings()
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!)
            } else {
                Result.failure(Exception("Error en el servidor: " + res.code()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitSighting(sightingDto: SightingDto): Result<SightingDto> = withContext(Dispatchers.IO) {
        try {
            val res = bioDexApiService.createSighting(sightingDto)
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!)
            } else {
                Result.failure(Exception("Error al subir registro: " + res.code()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSightingById(id: String): Result<SightingDto> {
        TODO("Not yet implemented")
    }
}