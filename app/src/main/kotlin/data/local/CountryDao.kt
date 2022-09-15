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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.tonyguyot.flagorama.data.local.model.CountryDetailsEntity
import io.github.tonyguyot.flagorama.data.local.model.CountryOverviewEntity

@Dao
interface CountryDao {

    @Query("SELECT * FROM country_overview_table WHERE region_key = :regionKey ORDER BY name ASC")
    fun selectCountriesByRegion(regionKey: String): List<CountryOverviewEntity>

    @Query("SELECT * FROM country_details_table WHERE country_iso3_code = :countryCode ORDER BY name ASC")
    fun selectCountryDetailsByCountryCode(countryCode: String): List<CountryDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countries: List<CountryOverviewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(countryDetails: CountryDetailsEntity)
}