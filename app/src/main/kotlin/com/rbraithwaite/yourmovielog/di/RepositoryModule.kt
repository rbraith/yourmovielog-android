package com.rbraithwaite.yourmovielog.di

import com.rbraithwaite.yourmovielog.core.repositories.MediaRepository
import com.rbraithwaite.yourmovielog.data.repositories.MediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMediaRepository(mediaRepositoryImpl: MediaRepositoryImpl): MediaRepository
}