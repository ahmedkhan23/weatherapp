package com.ahmed.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed.weatherapp.MainContent

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {

        composable(Screens.SplashScreen.route) {
            Splash(navController)
            /*
            LaunchedEffect("key1") {
                scope.launch {
                    delay(3000L)
                    navController.navigate(Screens.LandingPage.route) {
                        popUpTo(Screens.SplashScreen.route) {
                            inclusive = true
                        }
                    }

                }
            }*/
        }

        composable(Screens.LandingPage.route) {
            MainContent()
        }

    }
}