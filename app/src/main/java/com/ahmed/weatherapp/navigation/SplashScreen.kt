package com.ahmed.weatherapp.navigation

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ahmed.weatherapp.MainContent
import com.ahmed.weatherapp.R
import com.ahmed.weatherapp.data.PermissionAction
import com.ahmed.weatherapp.view.PermissionDialog


@Composable
fun Splash(navController: NavHostController) {

    val context = LocalContext.current
    var showContent by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.Black),
        contentAlignment = Alignment.Center) {

        Image(modifier = Modifier.fillMaxHeight(),
                painter = painterResource(R.drawable.splash_screen),
                contentDescription = "splash screen image",
                contentScale = ContentScale.FillHeight,
            )

        CircularProgressIndicator(modifier = Modifier.size(25.dp),
            colorResource(R.color.black),
            strokeWidth = 3.dp)


    }

    // location permissions flow
    PermissionDialog(context,
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        permissionAction = { permissionAction ->

            when (permissionAction) {
                is PermissionAction.PermissionGranted -> {
                    showContent = true
                }
                is PermissionAction.PermissionDenied -> {
                    showContent = false
                }
            }
        })

    if (showContent) {
        MainContent()
    }
    else {
        navController.popBackStack(Screens.SplashScreen.route, inclusive = true)
    }
}