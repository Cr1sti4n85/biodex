package com.example.biodex.core.di

import android.content.Context
import androidx.room.Room
import com.example.biodex.data.local.BioDexDatabase
import com.example.biodex.data.local.dao.SightingDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BioDexDatabase {
        return Room.databaseBuilder(
            context,
            BioDexDatabase::class.java,
            "biodex_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSightingDao(database: BioDexDatabase): SightingDAO {
        return database.sightingDao()
    }
}