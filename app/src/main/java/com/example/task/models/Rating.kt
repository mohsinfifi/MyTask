package com.example.task.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
) : Serializable