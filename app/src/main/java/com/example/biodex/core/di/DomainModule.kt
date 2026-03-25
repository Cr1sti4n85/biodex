package com.example.biodex.core.di

import com.example.biodex.domain.model.Sighting
import com.example.biodex.domain.repository.SightingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

//    @Provides
//    @Singleton
//    fun provideSightingRepository(): SightingRepository {
//        return object : SightingRepository {
//            override fun getAllSights() = flowOf(emptyList<Sighting>())
//
//            override suspend fun getSightingById(id: String) = null
//
//            override suspend fun createSighting(sighting: Sighting): Result<Boolean> {
//                return Result.success(true)
//            }
//
//            override suspend fun uploadImage(uri: String): Result<String> {
//                return Result.success("https://i.pinimg.com/736x/7f/af/b9/7fafb9d4b589a67f9115f9a258a2b4a4.jpg")
//            }
//
//        }
//    }
}