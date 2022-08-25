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
package io.github.tonyguyot.flagorama.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.tonyguyot.flagorama.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactUi(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerPanel(
                selectedMenuOption = NavMenuOption.GLOBAL,
                onDrawerClick = { scope.launch { drawerState.close() } }
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
fun NavigationDrawerPanel(
    selectedMenuOption: NavMenuOption,
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit = {},
    onOptionClick: (NavMenuOption) -> Unit = {}
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(24.dp)
    ) {
        NavigationDrawerPanelTitle(modifier, onDrawerClick)
        NavigationDrawerPanelItem(
            selected = selectedMenuOption == NavMenuOption.GLOBAL,
            text = stringResource(id = R.string.menu_global),
            icon = Icons.Default.Cloud,
            onClick = { onOptionClick(NavMenuOption.GLOBAL) }
        )
        NavigationDrawerPanelItem(
            selected = selectedMenuOption == NavMenuOption.FAVORITES,
            text = stringResource(id = R.string.menu_favorites),
            icon = Icons.Default.Favorite,
            onClick = { onOptionClick(NavMenuOption.FAVORITES) }
        )
    }
}

@Composable
fun NavigationDrawerPanelTitle(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name).uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = onDrawerClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(id = R.string.navigation_drawer)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerPanelItem(
    selected: Boolean,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        selected = selected,
        label = { Text(text = text, modifier = Modifier.padding(horizontal = 16.dp)) },
        icon = { Icon(imageVector = icon, contentDescription = text) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
        onClick = onClick
    )
}

enum class NavMenuOption {
    GLOBAL, FAVORITES
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
        FlagoramaNavHost(navController = navController, onDrawerClick = onDrawerClick)
    }
}
