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
package io.github.tonyguyot.flagorama.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.tonyguyot.flagorama.ui.screens.*

@Composable
fun AppNavHost(
    navController: NavHostController,
    onDrawerClick: () -> Unit
) {
    NavHost(navController = navController, startDestination = Destination.Home.route) {
        // Top level destinations
        composable(Destination.Home.route) {
            HomeScreen(onDrawerClick = onDrawerClick) { region ->
                navController.gotoCountryList(region.key)
            }
        }
        composable(Destination.Favorites.route) {
            FavoritesScreen(onDrawerClick = onDrawerClick) { country ->
                navController.gotoCountryDetails(country.code)
            }
        }
        composable(Destination.About.route) {
            AboutScreen(onOpenDrawerClick = onDrawerClick)
        }
        composable(Destination.Source.route) {
            SourceScreen(onOpenDrawerClick = onDrawerClick)
        }
        composable(Destination.Privacy.route) {
            PrivacyScreen(onOpenDrawerClick = onDrawerClick)
        }

        // Second level destinations
        composable("countries/{region}") { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("region")?.let { regionKey ->
                CountryListScreen(
                    regionKey = regionKey,
                    onBackClick = { navController.popBackStack() },
                    onClick = { country -> navController.gotoCountryDetails(country.code) }
                )
            }
        }

        // Third level destinations
        composable("country/{code}") { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("code")?.let { countryCode ->
                CountryDetailsScreen(countryCode = countryCode) {
                    navController.popBackStack()
                }
            }
        }
    }
}

fun NavHostController.gotoHome() = navigateFirstLevel("home")
fun NavHostController.gotoCountryList(regionKey: String) = navigate("countries/${regionKey}")
fun NavHostController.gotoCountryDetails(countryCode: String) = navigate("country/${countryCode}")

fun NavHostController.gotoFavorites() = navigateFirstLevel("favorites")
fun NavHostController.gotoAbout() = navigateFirstLevel("about")
fun NavHostController.gotoSource() = navigateFirstLevel("source")
fun NavHostController.gotoPrivacy() = navigateFirstLevel("privacy")

private fun NavHostController.navigateFirstLevel(route: String) {
    navigate(route) {
        popUpTo(0)
    }
}