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
package io.github.tonyguyot.flagorama.domain.repositories

import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.domain.model.CountryDetails
import io.github.tonyguyot.flagorama.domain.model.CountryOverview
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun observeCountriesByRegion(regionKey: String): Flow<Resource<List<CountryOverview>>>
    suspend fun observeCountryDetails(countryCode: String, forceFetch: Boolean = false): Flow<Resource<CountryDetails?>>
}