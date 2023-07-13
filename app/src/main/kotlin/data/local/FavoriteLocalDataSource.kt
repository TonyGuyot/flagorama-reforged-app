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
package io.github.tonyguyot.flagorama.data.local

import io.github.tonyguyot.flagorama.data.local.dao.FavoriteDao
import io.github.tonyguyot.flagorama.data.local.model.FavoriteEntity
import io.github.tonyguyot.flagorama.domain.model.CountryOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteLocalDataSource(private val dao: FavoriteDao) {

    fun getAllFavoriteCountries(): Flow<List<CountryOverview>> =
        dao.selectAllFavoriteCountries().map { favorites ->
            favorites.map { CountryLocalDataSource.toCountryOverview(it) }
        }

    fun isFavorite(countryCode: String): Flow<Boolean> =
        dao.isCountryFavorite(countryCode).map { it > 0 }

    suspend fun setAsFavorite(countryCode: String, favorite: Boolean) {
        val favoriteEntity = FavoriteEntity(countryCode)
        if (favorite) {
            dao.insert(favoriteEntity)
        } else {
            dao.delete(favoriteEntity)
        }
    }
}