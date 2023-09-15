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

import androidx.annotation.StringRes
import io.github.tonyguyot.flagorama.R

sealed class Region(val key: String, @StringRes val nameResId: Int) {
    data object Africa : Region("Africa", R.string.africa)
    data object Americas : Region("Americas", R.string.americas)
    data object Asia : Region("Asia", R.string.asia)
    data object Europe : Region("Europe", R.string.europe)
    data object Oceania : Region("Oceania", R.string.oceania)

    companion object {
        val all = listOf(Africa, Americas, Asia, Europe, Oceania)

        fun getByKeyOrNull(key: String): Region? = all.firstOrNull { it.key == key }
    }
}
