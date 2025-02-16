package com.ahmed.weatherapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed.weatherapp.R

@Preview
@Composable
fun Splash() {
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
}