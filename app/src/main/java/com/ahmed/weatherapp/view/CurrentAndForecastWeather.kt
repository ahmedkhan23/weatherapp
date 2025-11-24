package com.ahmed.weatherapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ahmed.weatherapp.domain.CompactWeatherData

@Composable
fun CurrentAndForecastedWeather(currentWeatherAndForecast: CurrentWeatherAndForecast) {
    Column(
        modifier =  Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        if (currentWeatherAndForecast.currentWeatherAndForecast.isEmpty()) {
            // TODO - update error here
            Text("Data Error")
        }
        else {
            Text(
                modifier = Modifier.padding(12.dp),
                text = currentWeatherAndForecast.currentWeatherAndForecast[0].city,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(4.dp))

            LazyColumn(modifier = Modifier.wrapContentSize()) {
                items(currentWeatherAndForecast.currentWeatherAndForecast) { compactWeatherData ->
                    DayWeatherItemComposable(compactWeatherData)
                }
            }

        }



    }
}

//@Preview
//@Composable
//fun CurrentAndForecastedWeatherPreview() {
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .background(color = Color.White)) {
//
//        CurrentAndForecastedWeather()
//        Spacer(modifier = Modifier.height(32.dp))
//        DayWeatherItemComposable()
//    }
//
//
//
//
//}

@Composable
fun DayWeatherItemComposable(compactWeatherData: CompactWeatherData) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.White)
        .padding(12.dp)
        .border(
            width = 1.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(12.dp)
        )){

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = compactWeatherData.date,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier.wrapContentWidth()
                .padding(start = 12.dp, top = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = compactWeatherData.temp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.width(8.dp))

            AsyncImage(
                model = compactWeatherData.currentWeatherIcon,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

        }

        Spacer(modifier = Modifier.height(4.dp))


        Row(
            modifier = Modifier.wrapContentWidth()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Hi: ${compactWeatherData.hiTemp}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Low: ${compactWeatherData.lowTemp}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

        }

    }

}

@Preview
@Composable
fun DayWeatherItemComposablePreview() {
    DayWeatherItemComposable(CompactWeatherData())
}