package com.ahmed.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ahmed.weatherapp.navigation.AppNavigation
import com.ahmed.weatherapp.ui.theme.WeatherAppTheme

val TAG = "WEATHER_APP"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate()")
        setContent {
            WeatherAppTheme {
                Log.d(TAG, " going to AppNavigation")
                AppNavigation()
            }
        }
    }
}




