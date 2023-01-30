package com.example.greedygameassignment.api

import com.example.greedygameassignment.api.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val apiKey = "043ac393dbfc590aefb7bea013bcf7f6"

//Api Interface for accessing all the api endpoints with queries

interface ApiService {

    @GET("?method=tag.getTopTags&api_key=${apiKey}&format=json")
    suspend fun getGenreList(): Response<GenreListResponse>

    @GET("?method=tag.getinfo&api_key=${apiKey}&format=json")
    suspend fun getGenreInfo(@Query("tag") tag: String): Response<GenreInfoResponse>

    @GET("?method=tag.gettopalbums&api_key=${apiKey}&format=json")
    suspend fun getGenreTopAlbums(@Query("tag") tag: String): Response<GenreTopAlbumsResponse>

    @GET("?method=tag.gettopartists&api_key=${apiKey}&format=json")
    suspend fun getGenreTopArtists(@Query("tag") tag: String): Response<GenreTopArtistsResponse>

    @GET("?method=tag.gettoptracks&api_key=${apiKey}&format=json")
    suspend fun getGenreTopTracks(@Query("tag") tag: String): Response<GenreTopTracksResponse>

    @GET("?method=album.getinfo&api_key=${apiKey}&format=json")
    suspend fun getAlbumInfo(
        @Query("artist") artist: String,
        @Query("album") album: String
    ): Response<AlbumInfoResponse>

    @GET("?method=artist.getinfo&api_key=${apiKey}&format=json")
    suspend fun getArtistInfo(@Query("artist") artist: String): Response<ArtistInfoResponse>

    @GET("?method=artist.gettoptags&api_key=${apiKey}&format=json")
    suspend fun getArtistTopTags(@Query("artist") artist: String): Response<ArtistTopTagsResponse>

    @GET("?method=artist.gettoptracks&api_key=${apiKey}&format=json")
    suspend fun getArtistTopTracks(@Query("artist") artist: String): Response<ArtistTopTracksResponse>

    @GET("?method=artist.gettopalbums&api_key=${apiKey}&format=json")
    suspend fun getArtistTopAlbums(@Query("artist") artist: String): Response<ArtistTopAlbumsResponse>

}