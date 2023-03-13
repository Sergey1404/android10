package com.example.lab10
import android.os.Bundle
import androidx.fragment.app.Fragment
object WeatherObject {
var weatherList = emptyList<WeatherNW.DataWeather>()
}
class WeatherFragment: Fragment(){
    var weatherList = emptyList<WeatherNW.DataWeather>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    companion object {
        const val TAG = "WEATHER_FRAGMENT"
    }
}