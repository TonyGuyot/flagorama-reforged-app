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
package io.github.tonyguyot.flagorama.domain.model

data class CountryDetails(
    val code: String,
    val iso2Code: String,
    val iso3Code: String,
    val name: String,
    val nativeNames: List<String>,
    val flagUrl: String,
    val capital: String,
    val subregion: String,
    val population: Long,
    val area: Double,
    val independent: Boolean,
    val unMember: Boolean
) {
    val fmtIsoCodes get() = listOf(iso2Code, iso3Code).joinToString()
    val fmtArea get() = String.format("%,.2f", area).removeSuffix(".00").removeSuffix(",00")
    val fmtPopulation get() = String.format("%,d", population)
}