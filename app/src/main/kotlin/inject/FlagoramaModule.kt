/*
 * Copyright (C) 2022 Tony Guyot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tonyguyot.flagorama.inject

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tonyguyot.flagorama.BuildConfig
import io.github.tonyguyot.flagorama.domain.repositories.CountryRepository
import io.github.tonyguyot.flagorama.data.DefaultCountryRepository
import io.github.tonyguyot.flagorama.data.DefaultFavoriteRepository
import io.github.tonyguyot.flagorama.data.local.AppDatabase
import io.github.tonyguyot.flagorama.data.local.CountryLocalDataSource
import io.github.tonyguyot.flagorama.data.local.FavoriteLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.CountryRemoteDataSource
import io.github.tonyguyot.flagorama.data.remote.RestCountriesService
import io.github.tonyguyot.flagorama.domain.repositories.FavoriteRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlagoramaModule {

    @Singleton
    @Provides
    fun provideCountryRepository(
        local: CountryLocalDataSource,
        remote: CountryRemoteDataSource
    ): CountryRepository {
        return DefaultCountryRepository(local, remote)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        local: FavoriteLocalDataSource
    ): FavoriteRepository {
        return DefaultFavoriteRepository(local)
    }

    @Singleton
    @Provides
    fun provideCountryLocalDataSource(database: AppDatabase): CountryLocalDataSource {
        return CountryLocalDataSource(database.countryDao())
    }

    @Singleton
    @Provides
    fun provideFavoriteLocalDataSource(database: AppDatabase): FavoriteLocalDataSource {
        return FavoriteLocalDataSource(database.favoriteDao())
    }

    @Singleton
    @Provides
    fun provideCountryRemoteDataSource(service: RestCountriesService): CountryRemoteDataSource {
        return CountryRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideRestCountriesService(retrofit: Retrofit): RestCountriesService {
        return retrofit.create(RestCountriesService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RestCountriesService.BASE_URL)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(Gson())

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DELETE FROM country_details_table")
            database.execSQL("ALTER TABLE country_details_table ADD country_iso2_code TEXT NOT NULL")
        }
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
}