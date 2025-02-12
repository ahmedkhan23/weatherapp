package com.ahmed.weatherapp.navigation

sealed class Screens(val route: String) {

    object SplashScreen : Screens("splash")
    object LandingPage : Screens("landingPage")
    object CurrentWeather : Screens("current_weather")
    object FavouritePlaces : Screens("favourites")
}