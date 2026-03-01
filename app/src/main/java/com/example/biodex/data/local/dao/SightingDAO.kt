package com.example.biodex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.biodex.data.local.entity.SightingEntity

@Dao
interface SightingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSighting(sighting: SightingEntity)

    @Query("SELECT * FROM sightings")
    suspend fun getAllSightings(): List<SightingEntity>
}