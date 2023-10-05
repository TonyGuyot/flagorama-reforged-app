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
package io.github.tonyguyot.flagorama.ui.navigation

import io.github.tonyguyot.flagorama.R
import io.github.tonyguyot.flagorama.ui.common.PanelItemData
import io.github.tonyguyot.flagorama.ui.common.PanelSectionData

val drawerPanelSections = listOf(
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
                textRes = R.string.menu_settings,
                iconRes = R.drawable.ic_action_settings,
                destination = Destination.Settings
            ),
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

