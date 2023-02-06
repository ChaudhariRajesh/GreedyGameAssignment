package com.example.greedygameassignment.repositories

import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.AlbumInfoResponse
import com.example.greedygameassignment.api.model.TrackInfoResponse
import javax.inject.Inject


class TrackRepository @Inject constructor(private val apiService: ApiService) : DataRepository() {
    suspend fun getTrackInfo(artist: String, album: String): Resource<TrackInfoResponse> {
        return genericApiCall { apiService.getTrackInfo(artist, album) }
    }
}