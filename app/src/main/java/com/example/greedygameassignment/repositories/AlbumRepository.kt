package com.example.greedygameassignment.repositories

import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.AlbumInfoResponse
import javax.inject.Inject

//Repository class, provides data related to Albums

class AlbumRepository @Inject constructor (private val apiService: ApiService) : DataRepository(){

    suspend fun getAlbumInfo(artist : String, album : String) : Resource<AlbumInfoResponse> {
        return genericApiCall { apiService.getAlbumInfo(artist, album) }
    }
}