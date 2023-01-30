package com.example.greedygameassignment.repositories

import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.*
import java.lang.Exception
import javax.inject.Inject

//Repository class, provides data related to Genres

class GenreRepository @Inject constructor (private val apiService : ApiService) : DataRepository() {

    suspend fun getGenreList() : Resource<GenreListResponse>{
        return genericApiCall { apiService.getGenreList() }
    }

    suspend fun getGenreInfo(genreName: String) : Resource<GenreInfoResponse>{
        return genericApiCall { apiService.getGenreInfo(genreName) }
    }

    suspend fun getGenreTopAlbums(genreName: String) : Resource<GenreTopAlbumsResponse> {
        return genericApiCall { apiService.getGenreTopAlbums(genreName) }
    }

    suspend fun getGenreTopArtists(genreName: String) : Resource<GenreTopArtistsResponse>{
      return genericApiCall { apiService.getGenreTopArtists(genreName) }
    }

    suspend fun getGenreTopTracks(genreName: String) : Resource<GenreTopTracksResponse> {
        return genericApiCall{ apiService.getGenreTopTracks(genreName) }
    }

}