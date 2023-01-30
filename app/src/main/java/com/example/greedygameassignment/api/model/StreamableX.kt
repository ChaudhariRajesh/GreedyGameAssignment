package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class StreamableX(
    @SerializedName("#text")
    val text: String,
    val fulltrack: String
)