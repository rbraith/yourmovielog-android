package com.rbraithwaite.untitledmovieapp.di

import com.google.gson.Gson
import com.rbraithwaite.untitledmovieapp.DebugConfig
import com.rbraithwaite.untitledmovieapp.data.network.LocalTmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugSingletonModule {
    @Singleton
    @Provides
    fun provideTmdbApiV3(retrofit: Retrofit, gson: Gson): TmdbApiV3 {
        if (DebugConfig.useLocalTmdbApiV3) {
            return LocalTmdbApiV3(gson)
        }

        return SingletonModule.createTmdbApiV3(retrofit)
    }
}