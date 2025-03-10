package com.ahmed.weatherapp.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ahmed.weatherapp.R
import com.ahmed.weatherapp.TAG
import com.ahmed.weatherapp.data.PermissionAction


@Preview()
@Composable
fun Splash(navigateToMain: () -> Unit = {},
           navigateBack: () -> Unit = {}) {

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


    // check if the permission has already been granted and if so, invoke the permissionAction fun
    // accordingly and return as we have nothing more to do
    val checkIfPermissionGranted by rememberIfPermissionGranted(LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION)

    if (checkIfPermissionGranted) {
        navigateToMain()
        return
    }

    Log.d(TAG, "about to launch permission dialog")

    // location permissions flow
    PermissionDialog(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        permissionAction = { permissionAction ->

            when (permissionAction) {
                is PermissionAction.PermissionGranted -> {
                    navigateToMain()
                }

                is PermissionAction.PermissionDenied -> {
                    navigateBack()
                }
            }
        })
}

@Composable
fun rememberIfPermissionGranted(context: Context, permission: String): MutableState<Boolean> {
    return remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED)
    }
}