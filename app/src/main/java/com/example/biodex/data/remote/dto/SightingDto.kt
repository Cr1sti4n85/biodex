package com.example.biodex.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SightingDto (
    @param:Json(name = "id")
    val id: String?,

    @param:Json(name = "title")
    val description: String,

    @param:Json(name = "latitude")
    val latitude: Double? = null,

    @param:Json(name = "longitude")
    val longitude: Double? = null,

    @param:Json(name = "photoURL")
    val photoURL: String? = null,

    @param:Json(name = "timestamp")
    val timestamp: String
)