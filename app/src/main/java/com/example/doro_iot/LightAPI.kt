package com.example.doro_iot

import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.POST

interface LightAPI {
    @POST("On")
    suspend fun controlOn(@Body smartFarmModel: LightModel): Response<Void>

    @POST("Off")
    suspend fun controlOff(@Body smartFarmModel: LightModel): Response<Void>

    @POST("Switch")
    suspend fun control(@Body smartFarmModel: LightModel): Response<Void>
}