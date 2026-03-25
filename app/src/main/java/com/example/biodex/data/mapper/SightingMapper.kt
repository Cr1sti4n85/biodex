package com.example.biodex.data.mapper

import com.example.biodex.data.local.entity.SightingEntity
import com.example.biodex.domain.model.Sighting
import java.util.Date

fun SightingEntity.toDomain(): Sighting {
    return Sighting(
        id = this.id.toString(),
        title = this.title,
        description = this.description,
        photoURL = this.photoURL,
        latitude = this.latitude,
        longitude = this.longitude,
        timestamp = Date(this.timestamp)
    )
}

fun Sighting.toEntity(): SightingEntity {
    return SightingEntity(
        id = if (this.id.isEmpty()) 0 else this.id.toLong(),
        title = this.title,
        description = this.description,
        photoURL = this.photoURL,
        latitude = this.latitude,
        longitude = this.longitude,
        timestamp = this.timestamp.time
    )
}