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
package io.github.tonyguyot.flagorama.data.local

import androidx.room.*
import io.github.tonyguyot.flagorama.data.local.model.CountryOverviewEntity
import io.github.tonyguyot.flagorama.data.local.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM country_overview_table WHERE country_iso3_code IN (SELECT country_iso3_code FROM favorite_table) ORDER BY name ASC")
    fun selectAllFavoriteCountries(): Flow<List<CountryOverviewEntity>>

    @Query("SELECT COUNT(country_iso3_code) FROM favorite_table WHERE country_iso3_code = :countryCode")
    fun isCountryFavorite(countryCode: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    suspend fun delete(favorite: FavoriteEntity)
}