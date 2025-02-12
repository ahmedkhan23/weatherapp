package com.ahmed.weatherapp.navigation

sealed class Screens(val route: String) {

    object CurrentWeather : Screens("current_weather")
    object FavouritePlaces : Screens("favourites")
}