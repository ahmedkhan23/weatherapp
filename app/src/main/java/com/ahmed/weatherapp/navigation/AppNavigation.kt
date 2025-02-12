package com.ahmed.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.CurrentWeather.route
    ) {

        composable(Screens.CurrentWeather.route) {

        }

        composable(Screens.FavouritePlaces.route) {

        }

    }
}