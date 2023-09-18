package com.example.doro_iot

import com.example.doro_iot.LightObject.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LightObject {
    private const val BASE_URL = "your arduino ip"

    private val getRetrofitLight by lazy {
        Retrofit.Builder()
            .baseUrl(LightObject.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    val getRetrofitLightService : LightAPI by lazy { getRetrofitLight.create(LightAPI::class.java) }


    fun getInstance(): LightAPI? {
        return getRetrofitLightService
    }
}