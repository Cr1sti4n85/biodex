package com.example.biodex.data.remote.source

import com.example.biodex.data.remote.dto.SightingDto

interface SightingRemoteDataSource {

    suspend fun fetchSightings(): Result<List<SightingDto>>
    suspend fun submitSighting(sightingDto: SightingDto): Result<SightingDto>
    suspend fun getSightingById(id: String): Result<SightingDto>
}