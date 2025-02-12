package com.ahmed.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ahmed.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                MainContent()
            }
        }
    }
}


@Preview
@Composable
fun MainContent() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {

            NavigationBar(
                containerColor = Color.Gray
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    label =  { Text(stringResource(R.string.local_weather)) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null)
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {},
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

        }

    }
}


