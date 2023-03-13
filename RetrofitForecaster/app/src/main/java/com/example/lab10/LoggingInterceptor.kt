package com.example.lab10

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber

class LoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if(response.body() == null){
            return response
        }
        val responseBody = response.body()
        val responseBodyString = response.body()?.string().orEmpty()

        val newResponse = response.newBuilder().body(
            ResponseBody.create(
                responseBody!!.contentType(),
                responseBodyString.toByteArray()
            )
        ).build()

        val t2 = System.nanoTime()
        Log.e("LOGGER", responseBodyString)
        return newResponse
    }
}