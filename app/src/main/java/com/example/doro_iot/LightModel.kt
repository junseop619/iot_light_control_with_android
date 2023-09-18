package com.example.doro_iot

import com.google.gson.annotations.SerializedName

data class LightModel(
    @SerializedName("switch")
    val ledControll: String,
)
