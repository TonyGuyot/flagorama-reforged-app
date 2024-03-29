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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.domain.model.CountryOverview
import io.github.tonyguyot.flagorama.domain.model.Region
import io.github.tonyguyot.flagorama.ui.common.ErrorMessage
import io.github.tonyguyot.flagorama.ui.common.WaitingIndicator
import io.github.tonyguyot.flagorama.ui.theme.AppTheme
import io.github.tonyguyot.flagorama.viewModel.CountryListViewModel

@Composable
fun CountryListScreen(
    regionKey: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCountryClick: (CountryOverview) -> Unit,
) {
    val regionName = Region.getByKeyOrNull(regionKey)?.let {
        stringResource(id = it.nameResId)
    } ?: regionKey
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { CountryListTopAppBar(regionName, onBackClick) }
    ) { paddingValues ->
        CountryListContent(modifier = Modifier.padding(paddingValues), onClick = onCountryClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryListTopAppBar(
    title: String,
    onNavigationClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.up_arrow_description)
                )
            }
        }
    )
}

@Composable
private fun CountryListContent(
    modifier: Modifier = Modifier,
    viewModel: CountryListViewModel = hiltViewModel(),
    onClick: (CountryOverview) -> Unit
) {
    val state by viewModel.uiState.collectAsState(Resource.loading())
    when (state.status) {
        Resource.Status.SUCCESS -> CountryOverviewGrid(modifier, state.data ?: emptyList(), onClick)
        Resource.Status.ERROR -> ErrorMessage(state.error, modifier)
        Resource.Status.LOADING -> WaitingIndicator(modifier)
    }
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
            .fillMaxHeight()
            .padding(all = 8.dp)
            .clickable {
                onClick(country)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = country.flagUrl,
            contentDescription = stringResource(R.string.flag_description, country.name)
        )
        Spacer(modifier = Modifier.fillMaxHeight())
        Text(
            text = country.name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            softWrap = true,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        CountryListScreen("Africa", Modifier, {}) {}
    }
}