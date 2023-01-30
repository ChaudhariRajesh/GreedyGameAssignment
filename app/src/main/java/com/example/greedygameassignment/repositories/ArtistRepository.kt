package com.example.greedygameassignment.repositories

import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.ArtistInfoResponse
import com.example.greedygameassignment.api.model.ArtistTopAlbumsResponse
import com.example.greedygameassignment.api.model.ArtistTopTagsResponse
import com.example.greedygameassignment.api.model.ArtistTopTracksResponse
import javax.inject.Inject

//Repository class, provides data related to Artists

class ArtistRepository @Inject constructor( private val apiService: ApiService) : DataRepository(){

    suspend fun getArtistInfo(artist : String) : Resource<ArtistInfoResponse> {
        return genericApiCall { apiService.getArtistInfo(artist) }
    }

    suspend fun getArtistTopTags(artist : String) : Resource<ArtistTopTagsResponse> {
        return genericApiCall{ apiService.getArtistTopTags(artist) }
    }

    suspend fun getArtistTopTracks(artist : String) : Resource<ArtistTopTracksResponse> {
        return genericApiCall { apiService.getArtistTopTracks(artist) }
    }

    suspend fun getArtistTopAlbums(artist : String) : Resource<ArtistTopAlbumsResponse> {
        return genericApiCall { apiService.getArtistTopAlbums(artist) }
    }

}