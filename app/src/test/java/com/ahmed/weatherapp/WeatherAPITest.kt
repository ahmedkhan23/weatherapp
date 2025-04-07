package com.ahmed.weatherapp

import com.ahmed.weatherapp.data.WeatherAPI
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class WeatherAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherAPI: WeatherAPI

    @Before
    fun setup() {
        // Setup MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockRequestDispatcher()
        mockWebServer.start()

        //Setup retrofit
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(
                contentType = "application/json".toMediaType()
            ))
            .build()

        weatherAPI = retrofit.create(WeatherAPI::class.java)
    }

    @Test
    fun `getCurrentWeather() returns a weather response`() = runTest {
        val response = weatherAPI.getCurrentWeather(lat = "43.6179245",
            lon = "-79.6673382",
            appid = BuildConfig.WEATHER_API_KEY)

//        assert(response.weather)

        assertThat(response.weather).isNotEmpty()

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }
}