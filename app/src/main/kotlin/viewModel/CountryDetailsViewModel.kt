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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.domain.model.CountryDetails
import io.github.tonyguyot.flagorama.domain.repositories.CountryRepository
import io.github.tonyguyot.flagorama.domain.repositories.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val countryCode: String = checkNotNull(savedStateHandle["code"])
    private val internUiState = MutableStateFlow<Resource<CountryDetails?>>(Resource.loading())
    val uiState: StateFlow<Resource<CountryDetails?>> = internUiState
    val favoriteState = favoriteRepository.observeCountryFavoriteStatus(countryCode)

    init {
        refreshUiState()
    }

    private fun refreshUiState() {
        viewModelScope.launch {
            countryRepository.observeCountryDetails(countryCode).collect {
                internUiState.value = it
            }
        }
    }

    fun setAsFavorite(favorite: Boolean) {
        viewModelScope.launch {
            favoriteRepository.setAsFavorite(countryCode, favorite)
        }
    }
}