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
package io.github.tonyguyot.flagorama.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.navigation.Destination
import io.github.tonyguyot.flagorama.ui.theme.monofettFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerPanel(
    sections: List<PanelSectionData>,
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onCloseDrawerClick: () -> Unit = {},
    onOptionClick: (PanelItemData) -> Unit = {}
) {
    val scrollState = rememberScrollState()

    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(24.dp)
                .verticalScroll(scrollState)
        ) {
            DrawerPanelTitle(modifier, onCloseDrawerClick)
            sections.forEachIndexed { index, section ->
                DrawerPanelHeadline(stringResource(section.titleRes))
                section.items.forEach { item ->
                    DrawerPanelItem(
                        selected = currentRoute == item.destination.route,
                        textResId = item.textRes,
                        iconResId = item.iconRes,
                        useIconOriginalColor = item.useIconOriginalColor,
                        onClick = { onOptionClick(item) }
                    )
                }
                if (index < sections.lastIndex) {
                    DrawerPanelDivider()
                }
            }
        }
    }
}

@Composable
private fun DrawerPanelTitle(
    modifier: Modifier = Modifier,
    onCloseDrawerClick: () -> Unit
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
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = monofettFontFamily,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = onCloseDrawerClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(id = R.string.close_drawer_description)
            )
        }
    }
}

@Composable
private fun DrawerPanelDivider() {
    Divider(
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun DrawerPanelHeadline(title: String) {
    Text(text = title.uppercase(), style = MaterialTheme.typography.titleMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerPanelItem(
    selected: Boolean,
    @StringRes textResId: Int,
    @DrawableRes iconResId: Int,
    useIconOriginalColor: Boolean,
    onClick: () -> Unit
) {
    val text = stringResource(textResId)
    NavigationDrawerItem(
        selected = selected,
        label = { Text(text = text, modifier = Modifier.padding(horizontal = 16.dp)) },
        icon = {
            if (useIconOriginalColor) {
                Icon(painter = painterResource(iconResId), contentDescription = text, tint = Color.Unspecified)
            } else {
                Icon(painter = painterResource(iconResId), contentDescription = text)
            }
        },
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
        onClick = onClick
    )
}

data class PanelItemData(
    @StringRes val textRes: Int,
    @DrawableRes val iconRes: Int,
    val destination: Destination,
    val useIconOriginalColor: Boolean = false
)

data class PanelSectionData(
    @StringRes val titleRes: Int,
    val items: List<PanelItemData>
)