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
package io.github.tonyguyot.flagorama.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.common.NavigationDrawerPanel
import io.github.tonyguyot.flagorama.ui.common.PanelItemData
import io.github.tonyguyot.flagorama.ui.common.PanelSectionData
import io.github.tonyguyot.flagorama.ui.navigation.AppNavHost
import io.github.tonyguyot.flagorama.ui.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun CompactUi(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val sections = listOf(
        PanelSectionData(
            titleRes = R.string.menu_headline_flags,
            items = listOf(
                PanelItemData(
                    textRes = R.string.menu_global,
                    iconRes = R.drawable.ic_action_globe,
                    destination = Destination.Home,
                    useIconOriginalColor = true
                ),
                PanelItemData(
                    textRes = R.string.menu_favorites,
                    iconRes = R.drawable.ic_action_fav_on,
                    destination = Destination.Favorites,
                    useIconOriginalColor = true
                )
            )
        ),
        PanelSectionData(
            titleRes = R.string.menu_headline_info,
            items = listOf(
                PanelItemData(
                    textRes = R.string.menu_about,
                    iconRes = R.drawable.ic_action_info,
                    destination = Destination.About
                ),
                PanelItemData(
                    textRes = R.string.menu_source,
                    iconRes = R.drawable.ic_action_github,
                    destination = Destination.Source
                ),
                PanelItemData(
                    textRes = R.string.menu_privacy,
                    iconRes = R.drawable.ic_action_key,
                    destination = Destination.Privacy
                )
            )
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerPanel(
                sections = sections,
                currentRoute = navBackStackEntry?.destination?.route,
                onCloseDrawerClick = { scope.launch { drawerState.close() } },
                onOptionClick = { option ->
                    navController.navigate(option.destination.route)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        MainContent(
            navController = navController,
            onDrawerClick = { scope.launch { drawerState.open() } }
        )
    }
}

@Composable
fun MainContent(
    navController: NavHostController,
    onDrawerClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavHost(navController = navController, onDrawerClick = onDrawerClick)
    }
}
