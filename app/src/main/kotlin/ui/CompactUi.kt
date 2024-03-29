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

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.tonyguyot.flagorama.ui.common.NavigationDrawerPanel
import io.github.tonyguyot.flagorama.ui.navigation.drawerPanelSections
import kotlinx.coroutines.launch

@Composable
fun CompactUi(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerPanel(
                sections = drawerPanelSections,
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
