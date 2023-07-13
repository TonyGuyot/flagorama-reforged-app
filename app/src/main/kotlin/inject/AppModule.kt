/*
 * Copyright (C) 2022-2023 Tony Guyot
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

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tonyguyot.flagorama.domain.repositories.CountryRepository
import io.github.tonyguyot.flagorama.data.DefaultCountryRepository
import io.github.tonyguyot.flagorama.data.DefaultFavoriteRepository
import io.github.tonyguyot.flagorama.data.local.CountryLocalDataSource
import io.github.tonyguyot.flagorama.data.local.FavoriteLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.CountryRemoteDataSource
import io.github.tonyguyot.flagorama.domain.repositories.FavoriteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}