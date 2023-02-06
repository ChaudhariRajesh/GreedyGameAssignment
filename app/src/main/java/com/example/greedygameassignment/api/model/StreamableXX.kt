package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class StreamableXX(
    @SerializedName("#text")
    val text: String,
    val fulltrack: String
)