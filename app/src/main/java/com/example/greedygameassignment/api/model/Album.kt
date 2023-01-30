package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("@attr")
    val attr: AttrXX,
    val artist: Artist,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val url: String
)