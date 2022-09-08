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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.github.tonyguyot.flagorama.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactUi(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedMenuOption by remember { mutableStateOf(NavMenuOption.GLOBAL) }
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerPanel(
                selectedMenuOption = selectedMenuOption,
                onDrawerClick = { scope.launch { drawerState.close() } },
                onOptionClick = { option ->
                    when (option) {
                        NavMenuOption.GLOBAL -> navController.gotoHome()
                        NavMenuOption.FAVORITES -> navController.gotoFavorites()
                        NavMenuOption.ABOUT -> navController.gotoAbout()
                        NavMenuOption.SOURCE -> navController.gotoSource()
                        NavMenuOption.PRIVACY -> navController.gotoPrivacy()
                    }
                    selectedMenuOption = option
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
private fun NavigationDrawerPanel(
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
        NavigationDrawerPanelHeadline(stringResource(R.string.menu_headline_flags))
        val mainMenu = listOf(
            Triple(NavMenuOption.GLOBAL, R.drawable.ic_action_globe, R.string.menu_global),
            Triple(NavMenuOption.FAVORITES, R.drawable.ic_action_fav_on, R.string.menu_favorites)
        )
        mainMenu.forEach { item ->
            NavigationDrawerPanelItem(
                selected = selectedMenuOption == item.first,
                textResId = item.third,
                iconResId = item.second,
                onClick = { onOptionClick(item.first) }
            )
        }
        NavigationDrawerPanelDivider()
        NavigationDrawerPanelHeadline(stringResource(R.string.menu_headline_info))
        val infoMenu = listOf(
            Triple(NavMenuOption.ABOUT, R.drawable.ic_action_info, R.string.menu_about),
            Triple(NavMenuOption.SOURCE, R.drawable.ic_action_github, R.string.menu_source),
            Triple(NavMenuOption.PRIVACY, R.drawable.ic_action_key, R.string.menu_privacy)
        )
        infoMenu.forEach { item ->
            NavigationDrawerPanelItem(
                selected = selectedMenuOption == item.first,
                textResId = item.third,
                iconResId = item.second,
                onClick = { onOptionClick(item.first) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerPanelTitle(
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

@Composable
private fun NavigationDrawerPanelDivider() {
    Divider(
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun NavigationDrawerPanelHeadline(title: String) {
    Text(text = title, style = MaterialTheme.typography.titleMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerPanelItem(
    selected: Boolean,
    @StringRes textResId: Int,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {
    val text = stringResource(textResId)
    NavigationDrawerItem(
        selected = selected,
        label = { Text(text = text, modifier = Modifier.padding(horizontal = 16.dp)) },
        icon = { Icon(painter = painterResource(iconResId), contentDescription = text, tint = Color.Unspecified) },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
        onClick = onClick
    )
}

enum class NavMenuOption {
    GLOBAL, FAVORITES, ABOUT, SOURCE, PRIVACY, /* CREDITS, REPORT */
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
