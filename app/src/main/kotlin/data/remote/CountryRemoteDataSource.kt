/*
 * Copyright (C) 2020 Tony Guyot
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
package io.github.tonyguyot.flagorama.data.remote

import io.github.tonyguyot.flagorama.data.remote.model.RestCountryDetails
import io.github.tonyguyot.flagorama.data.remote.model.RestCountryOverview
import io.github.tonyguyot.flagorama.data.utils.BaseRemoteDataSource
import io.github.tonyguyot.flagorama.domain.model.CountryDetails
import io.github.tonyguyot.flagorama.domain.model.CountryOverview

/**
 * Retrieve data about countries from the network.
 */
class CountryRemoteDataSource(private val service: RestCountriesService): BaseRemoteDataSource() {

    suspend fun fetchCountries(region: String) =
        fetchResource({ service.getCountriesByRegion(region) }) { restCountries ->
            restCountries.map { toCountryOverview(it) }
        }

    suspend fun fetchCountryDetails(countryCode: String) =
        fetchResource({ service.getCountryDetails(countryCode) }) { toCountryDetails(it.first()) }

    companion object Mapper {
        /** map a country network object to a country logic object */
        fun toCountryOverview(source: RestCountryOverview) = CountryOverview(
            id = source.code,
            name = source.name.common,
            flagUrl = source.flags.svgImageUrl,
            flag = source.flag
        )

        /** map a country network object to a country logic object */
        fun toCountryDetails(source: RestCountryDetails) = CountryDetails(
            id = source.code,
            name = source.name.common,
            nativeNames = source.name.nativeName.values.map { it.official },
            flagUrl = source.flags.svgImageUrl,
            region = source.subRegion ?: "",
            capital = source.capital.first(),
            population = source.population,
            area = source.area,
            independent = true,
            unMember = true
        )
    }
}