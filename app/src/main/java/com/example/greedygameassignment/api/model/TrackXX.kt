package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class TrackXX(
    @SerializedName("@attr")
    val attr: AttrXXXXXXXXXXX,
    val artist: ArtistXXXXXX,
    val image: List<ImageXXXXX>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)