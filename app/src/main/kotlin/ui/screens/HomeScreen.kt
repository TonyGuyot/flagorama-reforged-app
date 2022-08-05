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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.domain.model.Continent
import io.github.tonyguyot.flagorama.domain.model.continents
import io.github.tonyguyot.flagorama.ui.theme.FlagoramaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: (Continent) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { HomeTopAppBar() }
    ) { paddingValues ->
        ContinentList(modifier = Modifier.padding(paddingValues), onClick = onClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) }
    )
}

@Composable
fun ContinentList(
    modifier: Modifier = Modifier,
    onClick: (Continent) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(items = continents) { continent ->
                Button(
                    onClick = { onClick(continent) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = continent.nameResId))
                }
            }
        }
        Spacer(modifier = Modifier.weight(.5f))
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FlagoramaTheme {
        HomeScreen { }
    }
}