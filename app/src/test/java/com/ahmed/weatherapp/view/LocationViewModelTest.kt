package com.ahmed.weatherapp.view

import com.ahmed.weatherapp.data.LocationWeatherRepository
import com.ahmed.weatherapp.data.NetworkResult
import com.ahmed.weatherapp.data.model.WeatherResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocationViewModelTest {

    @Test
    fun successFullWeatherLoading() {


        val repo = InMemoryLocalWeatherRepository()
        val locationViewModel = LocationViewModel(locationRepository = repo, Dispatchers.Unconfined)

        locationViewModel.getCurrentWeather()

        assertThat(locationViewModel.locationDataState.value).isEqualTo(LocationWeatherData())


    }

    class InMemoryLocalWeatherRepository : LocationWeatherRepository {
        override suspend fun getCurrentLocation() {
            TODO("Not yet implemented")
        }

        override suspend fun getCurrentLocationWeather(): NetworkResult<WeatherResponse> {
            println()
            TODO("Not yet implemented")
        }

        override fun getLocationUpdates() {
            TODO("Not yet implemented")
        }

    }


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}