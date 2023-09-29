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
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.theme.AppTheme

@Composable
fun FlagScreen(
    countryName: String,
    flagUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize().background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        CountryFlag(flagUrl = flagUrl, countryName = countryName)
    }
}

@Composable
private fun CountryFlag(flagUrl: String, countryName: String) {
    AsyncImage(
        model = flagUrl,
        contentDescription = stringResource(R.string.flag_description, countryName),
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        FlagScreen("France", "https://flagcdn.com/fr.svg")
    }
}