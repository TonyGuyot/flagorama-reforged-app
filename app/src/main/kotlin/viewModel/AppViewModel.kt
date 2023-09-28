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
package io.github.tonyguyot.flagorama.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tonyguyot.flagorama.data.prefs.PrefsRepository
import io.github.tonyguyot.flagorama.domain.repositories.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
): ViewModel() {
    val useDynamicColorState = prefsRepository.readUseDynamicColors()
    val colorModeState = prefsRepository.readColorMode()

    fun updateUseDynamicColor(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.updateUseDynamicColors(value)
        }
    }

    fun updateColorMode(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsRepository.updateColorMode(value)
        }
    }
}