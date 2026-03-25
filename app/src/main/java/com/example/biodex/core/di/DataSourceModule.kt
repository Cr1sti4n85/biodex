package com.example.biodex.core.di

import com.example.biodex.data.remote.source.SightingRemoteDataSource
import com.example.biodex.data.remote.source.SightingRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSightingRemoteDataSource(
        sightingRemoteDataSourceImpl: SightingRemoteDataSourceImpl
    ): SightingRemoteDataSource

}