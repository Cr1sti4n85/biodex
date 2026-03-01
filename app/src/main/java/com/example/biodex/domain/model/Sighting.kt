package com.example.biodex.domain.model

import java.util.Date

data class Sighting(
    val id: String = "",
    val title: String,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val photoURL: String? = null,
    val timestamp: Date = Date()
)
