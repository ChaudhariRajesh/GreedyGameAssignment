package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("@attr")
    val attr: AttrXXXXXX,
    val artist: ArtistXX,
    val duration: String,
    val image: List<ImageXX>,
    val mbid: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)