package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class ArtistX(
    @SerializedName("@attr")
    val attr: AttrXXXX,
    val image: List<ImageX>,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)