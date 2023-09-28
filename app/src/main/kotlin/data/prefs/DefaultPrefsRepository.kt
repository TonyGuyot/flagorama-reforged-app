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
package io.github.tonyguyot.flagorama.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultPrefsRepository(private val context: Context) : PrefsRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("flagorama-preferences")

    override fun readUseDynamicColors(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_USE_DYNAMIC_COLOR] ?: DEFAULT_USE_DYNAMIC_COLOR
        }
    }

    override fun readColorMode(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_COLOR_MODE] ?: DEFAULT_COLOR_MODE
        }
    }

    override suspend fun updateUseDynamicColors(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USE_DYNAMIC_COLOR] = value
        }
    }

    override suspend fun updateColorMode(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_COLOR_MODE] = value
        }
    }

    companion object {
        private const val DEFAULT_USE_DYNAMIC_COLOR = true
        private const val DEFAULT_COLOR_MODE = 0

        private val KEY_USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_colors")
        private val KEY_COLOR_MODE = intPreferencesKey("color_mode")
    }
}