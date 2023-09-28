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

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.common.TopLevelAppBar
import io.github.tonyguyot.flagorama.ui.theme.AppTheme
import io.github.tonyguyot.flagorama.viewModel.AppViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onOpenDrawerClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopLevelAppBar(stringResource(R.string.title_settings), onOpenDrawerClick) }
    ) { paddingValues ->
        SettingsScreenContent(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val dynamicColor by appViewModel.useDynamicColorState.collectAsState(initial = true)
    val colorMode by appViewModel.colorModeState.collectAsState(initial = 0)

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = 25.dp)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // -- Theme section title
        SettingsHeader(stringResource(R.string.theme_section_title))

        // Dynamic colors selection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            SettingsOnOffItem(
                title = stringResource(R.string.theme_dynamic_color_title),
                description = stringResource(R.string.theme_dynamic_color_description),
                state = dynamicColor
            ) { newValue ->
                appViewModel.updateUseDynamicColor(newValue)
            }
        }

        // Light/dark mode selection
        SettingsDialogItem(
            title = stringResource(R.string.theme_color_mode_title),
            options = stringArrayResource(R.array.theme_color_mode_options),
            selected = colorMode
        ) { newValue ->
            appViewModel.updateColorMode(newValue)
        }
    }
}

@Composable
private fun SettingsHeader(subtitle: String) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun SettingsOnOffItem(
    title: String,
    description: String = "",
    state: Boolean,
    onChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Row(modifier = Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .width(width = 1.dp)
                        .height(60.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
                Switch(
                    modifier = Modifier.padding(10.dp),
                    checked = state,
                    onCheckedChange = onChange
                )
            }
        }
    }
}

@Composable
private fun SettingsDialogItem(
    title: String,
    options: Array<String>,
    selected: Int,
    onChange: (Int) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        MultipleSelectionDialog(
            options = options,
            selected = selected,
            onDismissRequest = { showDialog.value = false }
        ) { newSelection ->
            showDialog.value = false
            onChange(newSelection)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { showDialog.value = true }
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = options[selected],
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
private fun MultipleSelectionDialog(
    options: Array<String>,
    selected: Int,
    onDismissRequest: () -> Unit,
    onSelect: (Int) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                options.forEachIndexed { index, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (index == selected),
                                onClick = { onSelect(index) }
                            )
                            .padding(horizontal = 10.dp)
                    ) {
                        RadioButton(
                            selected = (index == selected),
                            onClick = { onSelect(index) }
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultSettingsScreenPreview() {
    AppTheme {
        SettingsScreen {}
    }
}