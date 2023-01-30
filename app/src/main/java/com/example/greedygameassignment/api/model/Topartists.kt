package com.example.greedygameassignment.api.model

import com.google.gson.annotations.SerializedName

data class Topartists(
    @SerializedName("@attr")
    val attr: AttrXXX,
    val artist: List<ArtistX>
)