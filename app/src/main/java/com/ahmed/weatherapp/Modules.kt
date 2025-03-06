package com.ahmed.weatherapp

import com.ahmed.weatherapp.data.LocationRepositoryImpl
import com.ahmed.weatherapp.data.LocationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModules = module {

    single { LocationRepositoryImpl(androidContext()) }

    single { LocationViewModel(get()) }
}