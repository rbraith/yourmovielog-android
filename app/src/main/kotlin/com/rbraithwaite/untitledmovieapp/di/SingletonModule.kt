package com.rbraithwaite.untitledmovieapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.core.repositories.TmdbRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.ReviewRepositoryImpl
import com.rbraithwaite.untitledmovieapp.data.database.AppDatabase
import com.rbraithwaite.untitledmovieapp.data.database.dao.CustomMediaDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import com.rbraithwaite.untitledmovieapp.data.repositories.CustomMediaRepositoryImpl
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiType
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiTypeDeserializer
import com.rbraithwaite.untitledmovieapp.data.network.result_call_adapter.ResultCallAdapterFactory
import com.rbraithwaite.untitledmovieapp.data.repositories.TmdbRepositoryImpl
import com.rbraithwaite.untitledmovietracker.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonBinderModule {
    @Binds
    abstract fun bindTmdbRepository(tmdbRepositoryImpl: TmdbRepositoryImpl): TmdbRepository

    @Binds
    abstract fun bindCustomMediaRepository(customMediaRepositoryImpl: CustomMediaRepositoryImpl): CustomMediaRepository

    @Binds
    abstract fun bindReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository
}


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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "movie-tracker-db").build()
    }

    @Singleton
    @Provides
    fun provideMediaDao(database: AppDatabase): MediaDao {
        return database.mediaDao()
    }

    @Singleton
    @Provides
    fun provideTmdbDao(database: AppDatabase): TmdbDao {
        return database.tmdbDao()
    }

    @Singleton
    @Provides
    fun provideCustomMediaDao(database: AppDatabase): CustomMediaDao {
        return database.customMediaDao()
    }

    @Singleton
    @Provides
    fun provideReviewDao(database: AppDatabase): ReviewDao {
        return database.reviewDao()
    }

    // *********************************************************
    // Network Dependencies
    // *********************************************************
    //region Network Dependencies

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return createGson()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        // http request/response logging
        val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        // headers to add to all requests
        val headersInterceptor = Interceptor { chain ->
            val updatedRequest = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_BEARER_TOKEN}")
                .build()

            chain.proceed(updatedRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()

        return createRetrofitBuilder(
            TmdbApiV3.Constants.BASE_URL.toHttpUrlOrNull()!!,
            gson
        ).client(client)
            .build()
    }

    // REFACTOR [23-12-10 11:13p.m.] -- not super happy about these methods, try to think of something
    //  better
    //  dunno if SingletonModule is the right place for them.

    fun createGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(SearchMultiType::class.java, SearchMultiTypeDeserializer())
        return builder.create()
    }

    fun createRetrofitBuilder(baseUrl: HttpUrl, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ResultCallAdapterFactory())
    }

    fun createTmdbApiV3(retrofit: Retrofit): TmdbApiV3 {
        return retrofit.create(TmdbApiV3::class.java)
    }

    //endregion

}