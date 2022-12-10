/*
 * Copyright (C) 2022 Tony Guyot
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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.domain.model.CountryOverview
import io.github.tonyguyot.flagorama.domain.repositories.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val repository: CountryRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val regionKey: String = checkNotNull(savedStateHandle["region"])
    private val internUiState = MutableStateFlow<Resource<List<CountryOverview>>>(Resource.loading())
    val uiState: StateFlow<Resource<List<CountryOverview>>> = internUiState

    init {
        refreshUiState()
    }

    private fun refreshUiState() {
        viewModelScope.launch {
            repository.observeCountriesByRegion(regionKey).collect {
                internUiState.value = it
            }
        }
    }
}