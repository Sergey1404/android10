package com.example.lab10

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Todo
interface WeatherAPI {
    companion object {
        fun createAPI(): WeatherAPI {
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createClient())
                .build()
            return retrofitBuilder.create(WeatherAPI::class.java)
        }
        private fun createClient(): OkHttpClient{
            val libLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val myLoggingInterceptor = LoggingInterceptor()
            return OkHttpClient.Builder()
                .addInterceptor(libLoggingInterceptor)
                .addInterceptor(myLoggingInterceptor)
                .build()
        }
    }

    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("appid") key: String,
        @Query("units") units: String,
        @Query("lang") language: String
    ): Call<WeatherNW>
}