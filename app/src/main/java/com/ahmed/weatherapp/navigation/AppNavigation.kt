package com.ahmed.weatherapp.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
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

    /** TODO - understand why this state is required and remove commented code
     * background info: navigatedToMain is added here because after
     * location flow takes place without this navigatedToMain and subsequent
     * check to compose Splash composable - for some reason Splash composable
     * was being invoked several times - in an indefinite loop
     * this state allows to get around this issue - however root cause needs to be known
     */
//    var navigatedToMain by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {

        composable(Screens.SplashScreen.route) {
//            if (!navigatedToMain) {
                Splash(
                    navigateToMain = {
                        navController.navigate(Screens.LandingPage.route)  {
                            popUpTo(Screens.SplashScreen.route) {
                                inclusive = true
                            }
//                            navigatedToMain = true
                        }
                    }, navigateBack = {
                        navController.popBackStack(Screens.SplashScreen.route, inclusive = true)
                        if (context is Activity) {
                            context.finish()
                        }
                    })
//            }

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