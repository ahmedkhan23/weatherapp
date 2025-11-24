package com.ahmed.weatherapp.domain

import com.ahmed.weatherapp.data.model.onecallmodels.OneCallResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class DayWeatherItemData(
    private val cityName: String,
    private val oneCallResponse: OneCallResponse,
    val dayIndex: Int) {

    fun toCompactWeatherData() : CompactWeatherData {

        return if (dayIndex in oneCallResponse.daily.indices) {

            CompactWeatherData(
                city = cityName,
                temp = getFormattedTempCelcius(oneCallResponse.daily[dayIndex].temp.day),
                hiTemp = getFormattedTempCelcius(oneCallResponse.daily[dayIndex].temp.max),
                lowTemp = getFormattedTempCelcius(oneCallResponse.daily[dayIndex].temp.min),
                currentWeatherIcon = getIconUrl(oneCallResponse.daily[dayIndex].weather[0].icon),
                date = getDateAndTime(dayOffSet = dayIndex).date
            )
        } else {
            CompactWeatherData()
        }
    }


}

private fun getIconUrl(iconUrl: String) : String {
    return "https://openweathermap.org/img/wn/${iconUrl}@2x.png"
}

private fun getDateAndTime(dayOffSet: Int): FormattedDateAndTime {

    val localDateTime = if (dayOffSet == 0) {
        LocalDateTime.now()
    }
    else {
        LocalDateTime.now().plusDays(dayOffSet.toLong())
    }

    val day = localDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val date = localDateTime.format(DateTimeFormatter.ofPattern("MMMM dd yyyy"))
    val time = localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))

    return FormattedDateAndTime(day, date, time)

}
private data class FormattedDateAndTime(
    val day: String = "", val date: String = "", val time: String = ""
)

private fun getFormattedTempCelcius(temp: Double): String {
    return String.format(Locale.getDefault(), "%.1f", temp).plus("\u00B0C")
}

data class CompactWeatherData(
    val city: String = "NA",
    val date: String = "NA",
    val temp: String = "NA",
    val hiTemp: String = "",
    val lowTemp: String = "",
    val currentWeatherIcon: String = ""
)