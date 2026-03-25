package com.example.biodex.core.di

import com.example.biodex.data.repository.SightingRepositoryImpl
import com.example.biodex.domain.repository.SightingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSightingRepository(
        sightingRepositoryImpl: SightingRepositoryImpl
    ): SightingRepository
}