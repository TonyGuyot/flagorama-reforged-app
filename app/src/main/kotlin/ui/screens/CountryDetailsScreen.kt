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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.domain.model.CountryDetails
import io.github.tonyguyot.flagorama.domain.model.CountryOverview
import io.github.tonyguyot.flagorama.ui.common.ErrorMessage
import io.github.tonyguyot.flagorama.ui.common.WaitingIndicator
import io.github.tonyguyot.flagorama.ui.theme.AppTheme
import io.github.tonyguyot.flagorama.viewModel.CountryDetailsViewModel

@Composable
fun CountryDetailsScreen(
    countryTitle: String,
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onFlagClick: (CountryOverview) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { CountryDetailsTopAppBar(countryTitle, viewModel, onBackClick) }
    ) { paddingValues ->
        CountryDetailsContent(Modifier.padding(paddingValues), viewModel, onFlagClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryDetailsTopAppBar(
    title: String,
    viewModel: CountryDetailsViewModel,
    onNavigationClick: () -> Unit
) {
    val isFavoriteState by viewModel.favoriteState.collectAsState(false)
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.up_arrow_description)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.setAsFavorite(!isFavoriteState)
                }
            ) {
                if (isFavoriteState) {
                    Icon(
                        painter = painterResource(R.drawable.ic_action_fav_on),
                        contentDescription = stringResource(R.string.menu_fav_set_off),
                        tint = Color.Unspecified
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_action_fav_off),
                        contentDescription = stringResource(R.string.menu_fav_set_on),
                    )
                }
            }
        }
    )
}

@Composable
private fun CountryDetailsContent(
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel,
    onFlagClick: (CountryOverview) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState(Resource.loading())
    when (state.status) {
        Resource.Status.SUCCESS -> state.data?.let { countryDetails ->
            CountryDetails(modifier, countryDetails) {
                onFlagClick(CountryOverview.fromCountryDetails(countryDetails))
            }
        }
        Resource.Status.ERROR -> ErrorMessage(state.error, modifier)
        Resource.Status.LOADING -> WaitingIndicator(modifier)
    }
}

@Composable
private fun CountryDetails(
    modifier: Modifier = Modifier,
    country: CountryDetails,
    onFlagClick: () -> Unit = {}
) {
    val name = country.name
    val flagUrl = country.flagUrl
    val nativeNames = country.nativeNames
    val unknown = stringResource(R.string.details_unknown)
    val info = listOf(
        Pair(stringResource(R.string.details_codes), country.fmtIsoCodes),
        Pair(stringResource(R.string.details_location), country.subregion),
        Pair(stringResource(R.string.details_capital), country.capital),
        Pair(
            stringResource(R.string.details_area),
            if (country.area < 0.0) unknown else "${country.fmtArea} km²"
        ),
        Pair(
            stringResource(R.string.details_population),
            if (country.population < 0) unknown else country.fmtPopulation
        )
    )
    BoxWithConstraints {
        val isPortrait = maxHeight > maxWidth
        if (isPortrait) {
            CountryDetailsPortrait(modifier, name, flagUrl, nativeNames, info, onFlagClick)
        } else {
            CountryDetailsLandscape(modifier, name, flagUrl, nativeNames, info, onFlagClick)
        }
    }
}

@Composable
private fun CountryDetailsPortrait(
    modifier: Modifier = Modifier,
    name: String,
    flagUrl: String,
    nativeNames: List<String>,
    info: List<Pair<String, String>>,
    onFlagClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(all = 8.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountryFlag(flagUrl, name, onFlagClick)
        CountryTitle(name)
        CountryNativeNames(nativeNames)
        CountryTableData(info)
        CountryDataSource()
    }
}

@Composable
private fun CountryDetailsLandscape(
    modifier: Modifier = Modifier,
    name: String,
    flagUrl: String,
    nativeNames: List<String>,
    info: List<Pair<String, String>>,
    onFlagClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CountryFlag(flagUrl, name, onFlagClick)
        }
        Column(
            modifier = modifier
                .weight(1f)
                .padding(all = 8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CountryTitle(name)
            CountryNativeNames(nativeNames)
            CountryTableData(info)
            CountryDataSource()
        }
    }
}

@Composable
private fun CountryFlag(flagUrl: String, countryName: String, onClick: () -> Unit = {}) {
    AsyncImage(
        model = flagUrl,
        contentDescription = stringResource(R.string.flag_description, countryName),
        modifier = Modifier.padding(horizontal = 10.dp).clickable { onClick() }
    )
}

@Composable
private fun CountryTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun CountryNativeNames(nativeNames: List<String>) {
    nativeNames.forEach { nativeName ->
        CountryNativeName(nativeName)
    }
}

@Composable
private fun CountryNativeName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CountryTableData(tableData: List<Pair<String, String>>) {
    val column1Weight = .4f // 40%
    val column2Weight = .6f // 60%

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        tableData.forEachIndexed { index, (id, text) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TableCell(text = id, weight = column1Weight, index = index, header = true)
                TableCell(text = text, weight = column2Weight, index = index)
            }
        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    index: Int,
    header: Boolean = false
) {
    val fgColor = if (index % 2 == 0) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }
    val bgColor = if (index % 2 != 0) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    Text(
        text = text,
        color = fgColor,
        fontWeight = if (header) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .weight(weight)
            .padding(1.dp)
            .background(bgColor)
            .padding(8.dp)
    )
}

@Composable
private fun CountryDataSource() {
    Text(stringResource(R.string.details_source))
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        CountryDetailsScreen("France")
    }
}