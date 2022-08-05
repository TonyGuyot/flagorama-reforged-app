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

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.tonyguyot.flagorama.ui.screens.CountryDetailsScreen
import io.github.tonyguyot.flagorama.ui.screens.CountryListScreen
import io.github.tonyguyot.flagorama.ui.screens.HomeScreen

@Composable
fun FlagoramaNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        // Top level destinations
        composable("home") {
            HomeScreen { continent ->
                navController.navigate("countries/${continent.key}")
            }
        }
        composable("favorites") { }
        composable("info") { }

        // Second level destinations
        composable("countries/{continent}") { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("continent")?.let {
                CountryListScreen(continent = it) { country ->
                    navController.navigate("country/${country.id}")
                }
            }
        }

        // Third level destinations
        composable("country/{code}") { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("code")?.let {
                CountryDetailsScreen(country = it)
            }
        }
    }
}