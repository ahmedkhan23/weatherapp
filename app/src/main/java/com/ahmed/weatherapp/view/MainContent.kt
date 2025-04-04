package com.ahmed.weatherapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ahmed.weatherapp.R
import com.ahmed.weatherapp.navigation.Screens
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun MainContent() {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var currentScreen by remember { mutableStateOf<Screens>(Screens.CurrentWeather) }
    val locationViewModel = koinViewModel<LocationViewModel>()
    val locationWeatherData by locationViewModel.locationDataState.collectAsStateWithLifecycle()

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(locationViewModel)
        onDispose { lifecycleOwner.lifecycle.removeObserver(locationViewModel) }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {

            NavigationBar(
                containerColor = Color.Gray
            ) {
                NavigationBarItem(
                    selected = currentScreen == Screens.CurrentWeather,
                    onClick = {
                        currentScreen = Screens.CurrentWeather


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

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)) {

            if (currentScreen == Screens.CurrentWeather) {

                if (!locationWeatherData.updated) {
                    CircularProgressIndicator()
                }
                else {

                    if(locationWeatherData.weather.error) {
                        Text(modifier = Modifier.wrapContentSize()
                            .padding(horizontal = 24.dp),
                            text = locationWeatherData.weather.errorMsg,
                            textAlign = TextAlign.Center)
                    }
                    else {
                        Column(modifier = Modifier.wrapContentSize(),
                            horizontalAlignment = Alignment.Start) {

                            Text(text = locationWeatherData.currentDateTime.day.plus(" ${locationWeatherData.currentDateTime.date}")
                                .plus(" ${locationWeatherData.currentDateTime.time}"))
                            Text(text = locationWeatherData.weather.name,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,)
                            Row(modifier = Modifier.wrapContentWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically) {


                                AsyncImage(
                                    model = locationWeatherData.iconUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = locationWeatherData.temp,
                                    fontSize = 22.sp)

                            }
                        }


                    }
                }


            }
            else if (currentScreen == Screens.FavouritePlaces){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red))
            }
        }

    }
}