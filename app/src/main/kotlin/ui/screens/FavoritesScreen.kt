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
package io.github.tonyguyot.flagorama.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.domain.model.CountryOverview
import io.github.tonyguyot.flagorama.ui.common.InfoMessage
import io.github.tonyguyot.flagorama.ui.common.TopLevelAppBar
import io.github.tonyguyot.flagorama.ui.theme.AppTheme
import io.github.tonyguyot.flagorama.viewModel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    onClick: (CountryOverview) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopLevelAppBar(stringResource(R.string.menu_favorites), onDrawerClick) }
    ) { paddingValues ->
        FavoriteCountryListContent(modifier = Modifier.padding(paddingValues), onClick = onClick)
    }
}

@Composable
private fun FavoriteCountryListContent(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
    onClick: (CountryOverview) -> Unit
) {
    val state by viewModel.uiState.collectAsState(initial = emptyList())
    if (state.isEmpty()) {
        NoFavorites(modifier)
    } else {
        CountryOverviewGrid(modifier, state, onClick)
    }
}

@Composable
private fun NoFavorites(
    modifier: Modifier = Modifier,
) {
    InfoMessage(message = stringResource(R.string.fav_empty), modifier = modifier)
}

@Composable
private fun CountryOverviewGrid(
    modifier: Modifier = Modifier,
    countries: List<CountryOverview>,
    onClick: (CountryOverview) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(all = 4.dp),
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(countries) { country ->
            CountryOverviewItem(country, onClick)
        }
    }
}

@Composable
private fun CountryOverviewItem(
    country: CountryOverview,
    onClick: (CountryOverview) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 4.dp)
            .clickable {
                onClick(country)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = country.flagUrl,
            contentDescription = stringResource(R.string.flag_description, country.name)
        )
        Text(country.name)
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        FavoritesScreen(Modifier, {}) {}
    }
}