package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class Albums(
    @SerializedName("@attr")
    val attr: AttrX,
    val album: List<Album>
)