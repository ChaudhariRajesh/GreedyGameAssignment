package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class AlbumXXX(
    @SerializedName("@attr")
    val attr: AttrXXXXXXXXXXXXX,
    val artist: String,
    val image: List<ImageXXXXXXX>,
    val mbid: String,
    val title: String,
    val url: String
)