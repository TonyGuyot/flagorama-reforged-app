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
package io.github.tonyguyot.flagorama.data.local

import io.github.tonyguyot.flagorama.data.local.model.CountryDetailsEntity
import io.github.tonyguyot.flagorama.data.local.model.CountryOverviewEntity
import io.github.tonyguyot.flagorama.domain.model.CountryDetails
import io.github.tonyguyot.flagorama.domain.model.CountryOverview

/**
 * Provide an abstraction to the local cache.
 * Perform read/write operations to the local cache and conversions to business logic objects.
 */
class CountryLocalDataSource(private val dao: CountryDao) {

    fun getCountriesByRegion(regionKey: String): List<CountryOverview> =
        dao.selectCountriesByRegion(regionKey).map { toCountryOverview(it) }

    fun getCountryDetails(countryCode: String): CountryDetails? =
        dao.selectCountryDetailsByCountryCode(countryCode).getOrNull(0)?.let {
            toCountryDetails(it)
        }

    fun saveCountries(countries: List<CountryOverview>, regionId: String) {
        dao.insertAll(countries.map {
            toCountryOverviewEntity(
                it,
                regionId
            )
        })
    }

    fun saveCountryDetails(countryDetails: CountryDetails?) {
        if (countryDetails != null) {
            dao.insert(toCountryDetailsEntity(countryDetails))
        }
    }

    companion object Mapper {
        /** map a country database entity to a country logic object */
        fun toCountryOverview(source: CountryOverviewEntity) = CountryOverview(
            code = source.code,
            name = source.name,
            flagUrl = source.flagUrl,
            flag = source.flag
        )

        /** map a country logic object to a country database entity */
        fun toCountryOverviewEntity(source: CountryOverview, regionKey: String) = CountryOverviewEntity(
            code = source.code,
            regionKey = regionKey,
            name = source.name,
            flagUrl = source.flagUrl,
            flag = source.flag
        )

        /** map a country details database entity to a country details logic object */
        fun toCountryDetails(source: CountryDetailsEntity) = CountryDetails(
            code = source.code,
            name = source.name,
            flagUrl = source.flagUrl,
            subregion = source.subregion,
            capital = source.capital,
            population = source.population,
            area = source.area,
            nativeNames = source.nativeNames.split(DELIMITER),
            independent = source.independent,
            unMember = source.unMember
        )

        /** map a country details logic object to a country details database entity */
        fun toCountryDetailsEntity(source: CountryDetails) = CountryDetailsEntity(
            code = source.code,
            name = source.name,
            flagUrl = source.flagUrl,
            subregion = source.subregion,
            capital = source.capital,
            population = source.population,
            area = source.area,
            nativeNames = source.nativeNames.joinToString(DELIMITER.toString()),
            independent = source.independent,
            unMember = source.unMember
        )

        private const val DELIMITER = '|'
    }
}