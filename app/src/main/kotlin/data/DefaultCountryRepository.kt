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

import io.github.tonyguyot.flagorama.data.local.CountryLocalDataSource
import io.github.tonyguyot.flagorama.data.remote.CountryRemoteDataSource
import io.github.tonyguyot.flagorama.data.utils.DatabaseFirstStrategy
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val local: CountryLocalDataSource,
    private val remote: CountryRemoteDataSource
) : CountryRepository {
    override fun observeCountriesByContinent(continentKey: String) =
        DatabaseFirstStrategy.getResultAsLiveData(
            databaseQuery = { local.getCountriesByContinent(continentKey) },
            shouldFetch = { it.isEmpty() },
            networkCall = { remote.fetchCountries(continentKey) },
            saveCallResult = { local.saveCountries(it, continentKey) }
        )

    override fun observeCountryDetails(countryCode: String) =
        DatabaseFirstStrategy.getResultAsLiveData(
            databaseQuery = { local.getCountryDetails(countryCode) },
            shouldFetch = { it == null },
            networkCall = { remote.fetchCountryDetails(countryCode) },
            saveCallResult = { local.saveCountryDetails(it) }
        )
}