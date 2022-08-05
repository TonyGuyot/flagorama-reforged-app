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
package io.github.tonyguyot.flagorama.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryDetailsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "native_name")
    val nativeName: String,

    @ColumnInfo(name = "flag_url")
    val flagUrl: String,

    @ColumnInfo(name = "region")
    val region: String,

    @ColumnInfo(name = "capital")
    val capital: String,

    @ColumnInfo(name = "population")
    val population: Long,

    @ColumnInfo(name = "area")
    val area: Double,

    @ColumnInfo(name = "independent")
    val independent: Boolean,

    @ColumnInfo(name = "unMember")
    val unMember: Boolean
)
