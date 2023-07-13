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
package io.github.tonyguyot.flagorama

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import io.github.tonyguyot.flagorama.ui.MainUi
import io.github.tonyguyot.flagorama.ui.theme.AppTheme

import org.junit.Test

import org.junit.Rule

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAllButtonsAreVisible() {
        // Start the app
        composeTestRule.setContent {
            val navController = rememberNavController()
            AppTheme {
                MainUi(windowSize = WindowWidthSizeClass.Compact, navController = navController)
            }
        }

        // check all buttons are there
        composeTestRule.onNodeWithText("Africa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Americas").assertIsDisplayed()
        composeTestRule.onNodeWithText("Asia").assertIsDisplayed()
        composeTestRule.onNodeWithText("Europe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Oceania").assertIsDisplayed()
    }

    @Test
    fun testDrawerPanelIsActionable() {
        // Start the app
        composeTestRule.setContent {
            val navController = rememberNavController()
            AppTheme {
                MainUi(windowSize = WindowWidthSizeClass.Compact, navController = navController)
            }
        }

        // open the drawer panel
        composeTestRule.onNodeWithContentDescription("Open navigation drawer").performClick()

        // check drawer panel is open
        //composeTestRule.onNodeWithText("Flagorama").assertIsDisplayed()
    }
}
