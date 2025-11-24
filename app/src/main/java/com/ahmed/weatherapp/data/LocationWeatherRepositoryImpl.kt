package com.ahmed.weatherapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ahmed.weatherapp.BuildConfig
import com.ahmed.weatherapp.R
import com.ahmed.weatherapp.TAG
import com.ahmed.weatherapp.data.model.WeatherResponse
import com.ahmed.weatherapp.domain.CompactWeatherData
import com.ahmed.weatherapp.domain.DayWeatherItemData
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class LocationWeatherRepositoryImpl(
    private val context: Context,
    private val weatherAPI: WeatherAPI) : LocationWeatherRepository {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation() {

        val cancellationTokenSource = CancellationTokenSource()

        val currentLocationRequest = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationProviderClient.getCurrentLocation(currentLocationRequest, cancellationTokenSource.token)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                println("couldn't find location ${it.message}")
            }
    }



    override fun getLocationUpdates() {

        // TODO -
    }

    /**
     * Retrieves the current weather for the user's location.
     *
     * This function attempts to retrieve the user's current location using the fused location provider.
     * If the location is successfully retrieved, it fetches the weather data from the Weather API.
     * The function handles errors during location retrieval and API calls, returning appropriate error messages.
     *
     * @return A [NetworkResult] containing either the weather data ([WeatherResponse]) on success
     *         or an error message on failure.
     */
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocationWeather(): NetworkResult<WeatherResponse> {

        val location = try {
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        } catch (e: Exception) {
            Log.d(TAG, "unable to retrieve location")
            return NetworkResult.Error(context.getString(R.string.location_retrieve_error))
        }

        return try {
            val weatherResponse = weatherAPI.getCurrentWeather(location.latitude.toString(),
                location.longitude.toString(), BuildConfig.WEATHER_API_KEY)

            NetworkResult.Success(weatherResponse)
        } catch (e: Exception) {
            NetworkResult.Error("Error ${e.message ?: " unknown"}")
        }
    }

    /**
     * Retrieves the weather forecast and current weather for the user's location.
     *
     * This function first attempts to retrieve the user's current location using the fused location provider.
     * If successful, it fetches the weather forecast data from the OneCall API and the current weather data
     * from the Weather API. The data is then processed into a list of compact weather data objects.
     *
     * @return A [NetworkResult] containing either a list of [CompactWeatherData] on success
     *         or an error message on failure.
     */
    @SuppressLint("MissingPermission")
    override suspend fun getLocationWeatherAndForecast(): NetworkResult<List<CompactWeatherData>> {
        val location = try {
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        } catch (e: Exception) {
            Log.d(TAG, "unable to retrieve location")
            return NetworkResult.Error(context.getString(R.string.location_retrieve_error))
        }

        val allWeatherResponse = weatherAPI.getAllWeather(
            lat = location.latitude.toString(),
            lon = location.longitude.toString(),
            appid = BuildConfig.WEATHER_API_KEY
        )

        if (allWeatherResponse.isSuccessful) {
            Log.d(TAG, allWeatherResponse.body().toString())

            // we have data from onecall api, let's get weather api call data
            try {
                val weatherResponse = weatherAPI.getCurrentWeather(location.latitude.toString(),
                    location.longitude.toString(), BuildConfig.WEATHER_API_KEY)

                val compactWeatherDataList = ArrayList<CompactWeatherData>()

                if (allWeatherResponse.body() != null) {
                    for (daily in allWeatherResponse.body()!!.daily.withIndex()) {
                        compactWeatherDataList.add(DayWeatherItemData(weatherResponse.name, allWeatherResponse.body()!!, dayIndex = daily.index)
                            .toCompactWeatherData())
                    }
                }

                return NetworkResult.Success(compactWeatherDataList)

            } catch (e: Exception) {
                return NetworkResult.Error("Error ${e.message ?: " unknown"}")
            }

        }
        else {
            return NetworkResult.Error("Error ${getErrorMsg(allWeatherResponse.code()) ?: " unknown"}")
        }


    }

    /**
     * Maps HTTP status codes to user-friendly error messages.
     *
     * @param code The HTTP status code to map.
     * @return A user-friendly error message corresponding to the given status code.
     */
    private fun getErrorMsg(code: Int): String {
        return when (code) {
            400 -> context.getString(R.string.bad_http_request)
            401 -> context.getString(R.string.unauthorized_check_app_key)
            403 -> context.getString(R.string.no_permissions)
            404 -> context.getString(R.string.resource_not_found)
            500 -> context.getString(R.string.internal_server_error)
            503 -> context.getString(R.string.server_unavailable)
            else -> context.getString(R.string.unknown_error) + " $code"
        }
    }

}