package com.example.biodex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.biodex.data.local.entity.SightingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SightingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSighting(sighting: SightingEntity)

    @Query("SELECT * FROM sightings ORDER BY timestamp DESC")
    fun getAllSightings(): Flow<List<SightingEntity>>

    @Query("SELECT * FROM sightings WHERE id = :id")
    suspend fun getSightingById(id: Long): SightingEntity?

    @Query("DELETE FROM sightings WHERE id = :id")
    suspend fun deleteSightingById(id: Long)
}