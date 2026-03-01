package com.example.biodex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.biodex.data.local.dao.SightingDAO
import com.example.biodex.data.local.entity.SightingEntity

@Database(entities = [SightingEntity::class], version = 1, exportSchema = false)
abstract class BioDexDatabase: RoomDatabase() {
    abstract fun sightingDao(): SightingDAO
}