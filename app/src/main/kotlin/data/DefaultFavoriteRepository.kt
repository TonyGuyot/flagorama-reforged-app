/*
 * Copyright (C) 2021 Tony Guyot
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
package io.github.tonyguyot.flagorama.data

import io.github.tonyguyot.flagorama.data.local.FavoriteLocalDataSource
import io.github.tonyguyot.flagorama.domain.repositories.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val local: FavoriteLocalDataSource
) : FavoriteRepository {
    override fun observeFavoriteCountryCodes(): Flow<List<String>> {
        return local.getAllFavoriteCountryCodes()
    }

    override fun observeCountryFavoriteStatus(countryCode: String): Flow<Boolean> {
        return local.isFavorite(countryCode)
    }

    override suspend fun setAsFavorite(countryCode: String, favorite: Boolean) {
        return local.setAsFavorite(countryCode, favorite)
    }
}