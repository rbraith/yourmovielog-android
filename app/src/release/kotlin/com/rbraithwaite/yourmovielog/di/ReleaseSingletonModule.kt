package com.rbraithwaite.yourmovielog.di

import com.rbraithwaite.yourmovielog.data.network.TmdbApiV3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReleaseSingletonModule {
    @Singleton
    @Provides
    fun provideTmdbApiV3(retrofit: Retrofit): TmdbApiV3 {
        return SingletonModule.createTmdbApiV3(retrofit)
    }
}