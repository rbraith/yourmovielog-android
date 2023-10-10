package com.rbraithwaite.untitledmovieapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.database.AppDatabase
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.media.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Singleton
    @Provides
    fun provideLoggingTree(): Timber.Tree {
        return Timber.DebugTree()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IoDispatcher

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultDispatcher

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Singleton
    @Provides
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Singleton
    @Provides
    fun provideMediaRepository(
        coroutineScope: CoroutineScope,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        mediaDao: MediaDao
    ): MediaRepository {
        return MediaRepositoryImpl(
            coroutineScope,
            coroutineDispatcher,
            mediaDao
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "movie-tracker-db").build()
    }

    @Singleton
    @Provides
    fun provideMediaDao(database: AppDatabase): MediaDao {
        return database.mediaDao()
    }
}