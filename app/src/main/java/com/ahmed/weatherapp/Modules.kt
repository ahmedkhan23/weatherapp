package com.ahmed.weatherapp

import com.ahmed.weatherapp.data.LocationWeatherRepository
import com.ahmed.weatherapp.data.LocationWeatherRepositoryImpl
import com.ahmed.weatherapp.data.LocationViewModel
import com.ahmed.weatherapp.data.WeatherAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {

    viewModel { LocationViewModel(get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(
                Json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://api.openweathermap.org/")
            .build()
    }

    single { get<Retrofit>().create(WeatherAPI::class.java) }

    single<LocationWeatherRepository> { LocationWeatherRepositoryImpl(get(), get())}



}