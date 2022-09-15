/*
 * Copyright (C) 2021 Tony Guyot
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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.data.utils.Resource
import io.github.tonyguyot.flagorama.domain.model.CountryDetails
import io.github.tonyguyot.flagorama.ui.common.ErrorMessage
import io.github.tonyguyot.flagorama.ui.common.WaitingIndicator
import io.github.tonyguyot.flagorama.ui.theme.FlagoramaTheme
import io.github.tonyguyot.flagorama.viewModel.CountryDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailsScreen(
    countryCode: String,
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { CountryDetailsTopAppBar(countryCode, viewModel, onBackClick) }
    ) { paddingValues ->
        CountryDetailsContent(Modifier.padding(paddingValues), viewModel)
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
    CenterAlignedTopAppBar(
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
                val descriptionResId = if (isFavoriteState) R.string.menu_fav_set_off else R.string.menu_fav_set_on
                val iconResId = if (isFavoriteState) R.drawable.ic_action_fav_on else R.drawable.ic_action_fav_off
                Icon(
                    painter = painterResource(iconResId),
                    contentDescription = stringResource(descriptionResId),
                    tint = Color.Unspecified
                )
            }
        }
    )
}

@Composable
fun CountryDetailsContent(
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel
) {
    val state by viewModel.uiState.observeAsState(Resource.loading())
    when (state.status) {
        Resource.Status.SUCCESS -> state.data?.let { CountryDetails(modifier, it) }
        Resource.Status.ERROR -> ErrorMessage(modifier)
        Resource.Status.LOADING -> WaitingIndicator(modifier)
    }
}

@Composable
private fun CountryDetails(
    modifier: Modifier = Modifier,
    country: CountryDetails
) {
    Column(
        modifier = modifier.padding(all = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountryFlag(country.flagUrl, country.name)
        CountryTitle(country.name)
        CountryNativeNames(country.nativeNames)
        CountryTableData(
            listOf(
                Pair("Where:", country.subregion),
                Pair("Capital", country.capital),
                Pair("Area", country.area.toString()),
                Pair("Population", country.population.toString())
            )
        )
    }
}

@Composable
private fun CountryFlag(flagUrl: String, countryName: String) {
    AsyncImage(
        model = flagUrl,
        contentDescription = stringResource(R.string.flag_description, countryName)
    )
}

@Composable
private fun CountryTitle(title: String) {
    Text(title, style = MaterialTheme.typography.headlineSmall)
}

@Composable
private fun CountryNativeNames(nativeNames: List<String>) {
    nativeNames.forEach { nativeName ->
        CountryNativeName(nativeName)
    }
}

@Composable
private fun CountryNativeName(name: String) {
    Text(name, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun CountryTableData(tableData: List<Pair<String, String>>) {
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        items(items = tableData) {
            val (id, text) = it
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = id, weight = column1Weight)
                TableCell(text = text, weight = column2Weight)
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FlagoramaTheme {
        CountryDetailsScreen("FRA") {}
    }
}