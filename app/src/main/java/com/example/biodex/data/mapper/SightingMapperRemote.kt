package com.example.biodex.data.mapper

import com.example.biodex.data.remote.dto.SightingDto
import com.example.biodex.domain.model.Sighting
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun parseApiDate(date: String): Date {
    return try {
        Date(date.toLong())
    } catch (e: NumberFormatException){
        try{
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.parse(date) ?: Date()
        } catch (e: Exception){
            Date()
        }
    }
}

fun SightingDto.toDomain(): Sighting {
    return Sighting(
        id = this.id ?: "",
        title = this.description,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude,
        photoURL = this.photoURL,
        timestamp = parseApiDate(this.timestamp)

    )
}

fun Sighting.toDto(): SightingDto {
    return SightingDto(
        id = this.id.ifEmpty { null },
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude,
        photoURL = this.photoURL,
        timestamp = this.timestamp.time.toString()

    )
}