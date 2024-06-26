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
package io.github.tonyguyot.flagorama.data.remote.model

import com.google.gson.annotations.SerializedName

data class RestCountryDetails(
    @field:SerializedName("cca2")
    val iso2Code: String,

    @field:SerializedName("cca3")
    val iso3Code: String,

    @field:SerializedName("name")
    val name: RestCountryName,

    @field:SerializedName("flags")
    val flags: RestCountryFlags,

    @field:SerializedName("subregion")
    val subregion: String?,

    @field:SerializedName("capital")
    val capital: List<String>?,

    @field:SerializedName("population")
    val population: Long,

    @field:SerializedName("area")
    val area: Double,

    @field:SerializedName("independent")
    val independent: Boolean,

    @field:SerializedName("unMember")
    val unMember: Boolean
)
