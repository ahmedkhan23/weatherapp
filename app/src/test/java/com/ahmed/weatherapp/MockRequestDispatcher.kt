package com.ahmed.weatherapp

import com.google.common.io.Resources
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

class MockRequestDispatcher: Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {

        val appId = BuildConfig.WEATHER_API_KEY

        return when (request.path) {
            "/data/2.5/weather?lat=43.6179245&lon=-79.6673382&appid=${appId}&units=metric" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("weatherresponse.json"))
            }
            else -> throw IllegalArgumentException("unknown request path ${request.path}")
        }


    }

    private fun getJson(path: String): String {
        val uri = Resources.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}