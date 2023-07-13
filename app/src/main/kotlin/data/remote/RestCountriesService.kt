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
package io.github.tonyguyot.flagorama.data.remote

import io.github.tonyguyot.flagorama.data.remote.model.RestCountryOverview
import io.github.tonyguyot.flagorama.data.remote.model.RestCountryDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Restcountries API access points
 */
interface RestCountriesService {

    @GET("/v3.1/region/{region}?fields=cca3,name,flag,flags")
    suspend fun getCountriesByRegion(@Path("region") regionCode: String): Response<List<RestCountryOverview>>

    @GET("/v3.1/alpha/{code}")
    suspend fun getCountryDetails(@Path("code") countryCode: String): Response<List<RestCountryDetails>>

    companion object {
        const val BASE_URL = "https://restcountries.com"
    }
}