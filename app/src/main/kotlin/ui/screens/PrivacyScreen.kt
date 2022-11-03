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
package io.github.tonyguyot.flagorama.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tonyguyot.flagorama.ui.common.TopLevelAppBar
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.common.InfoCard
import io.github.tonyguyot.flagorama.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(
    modifier: Modifier = Modifier,
    onOpenDrawerClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopLevelAppBar(stringResource(R.string.title_privacy), onOpenDrawerClick) }
    ) { paddingValues ->
        PrivacyScreenContent(
            Modifier
                .fillMaxSize()
                .padding(paddingValues))
    }
}

@Composable
private fun PrivacyScreenContent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = 15.dp)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val titles = stringArrayResource(R.array.privacy_titles)
        val contents = stringArrayResource(R.array.privacy_contents)
        titles.forEachIndexed { index, title ->
            InfoCard(title = title, content = contents[index])
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPrivacyScreenPreview() {
    AppTheme {
        PrivacyScreen {}
    }
}