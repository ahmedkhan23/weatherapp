package com.ahmed.weatherapp.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed.weatherapp.MainContent
import com.ahmed.weatherapp.view.Splash

@Composable
fun AppNavigation() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {

        composable(Screens.SplashScreen.route) {
                Splash(
                    navigateToMain = {
                        navController.navigate(Screens.LandingPage.route)  {
                            popUpTo(Screens.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }, navigateBack = {
                        navController.popBackStack(Screens.SplashScreen.route, inclusive = true)
                        if (context is Activity) {
                            context.finish()
                        }
                    })

        }

        composable(Screens.LandingPage.route) {
            MainContent()
        }

    }
}