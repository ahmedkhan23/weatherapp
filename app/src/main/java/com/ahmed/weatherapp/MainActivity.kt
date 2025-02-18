package com.ahmed.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ahmed.weatherapp.navigation.AppNavigation
import com.ahmed.weatherapp.navigation.Screens
import com.ahmed.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Log.d(TAG, " going to AppNavigation")
                AppNavigation()
            }
        }
    }
}


@Preview
@Composable
fun MainContent() {

    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf<Screens>(Screens.CurrentWeather) }

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {

            NavigationBar(
                containerColor = Color.Gray
            ) {
                NavigationBarItem(
                    selected = currentScreen == Screens.CurrentWeather,
                    onClick = {
                        currentScreen = Screens.CurrentWeather

                        Toast.makeText(context, "currentWeatherSelected", Toast.LENGTH_SHORT).show()
                    },
                    label =  { Text(stringResource(R.string.local_weather)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null)
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == Screens.FavouritePlaces,
                    onClick = {
                        currentScreen = Screens.FavouritePlaces

                        Toast.makeText(context, "Favourites Selected", Toast.LENGTH_SHORT).show()
                    },
                    label =  { Text(stringResource(R.string.favourites)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null)
                    }
                )

            }

        }) { innerPadding ->

                Column(modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)) {

                    if (currentScreen == Screens.CurrentWeather) {
                        Box(modifier = Modifier.fillMaxSize()
                            .background(Color.Blue))
                    }
                    else if (currentScreen == Screens.FavouritePlaces){
                        Box(modifier = Modifier.fillMaxSize()
                            .background(Color.Red))
                    }
                }

    }
}


