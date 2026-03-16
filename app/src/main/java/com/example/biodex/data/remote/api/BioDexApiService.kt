package com.example.biodex.data.remote.api

import com.example.biodex.data.remote.dto.SightingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BioDexApiService {

    @GET("sightings")
    suspend fun getSightings(): Response<List<SightingDto>>

    @POST("sightings")
    suspend fun createSighting(@Body sighting: SightingDto): Response<SightingDto>

    @GET("sightings/{id}")
    suspend fun getSightingById(@Path("id") id: String): Response<SightingDto>


}