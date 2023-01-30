package com.example.greedygameassignment.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.*
import com.example.greedygameassignment.repositories.ArtistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel for the ArtistRepository

@HiltViewModel
class ArtistInfoViewModel @Inject constructor(private val repository: ArtistRepository) :
    ViewModel() {

    private val _artistInfo = MutableLiveData<Resource<ArtistInfoResponse>>()
    val artistInfo: LiveData<Resource<ArtistInfoResponse>>
        get() = _artistInfo

    fun getArtistInfo(artist: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _artistInfo.postValue(Resource.Loading())
            val listResult = repository.getArtistInfo(artist)
            _artistInfo.postValue(listResult)
        }
    }

    private val _artistTopTags = MutableLiveData<Resource<ArtistTopTagsResponse>>()
    val artistTopTags: LiveData<Resource<ArtistTopTagsResponse>>
        get() = _artistTopTags

    fun getArtistTopTags(artist: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getArtistTopTags(artist)
            _artistTopTags.postValue(listResult)
        }
    }

    private val _artistTopTracks = MutableLiveData<Resource<ArtistTopTracksResponse>>()
    val artistTopTracks: LiveData<Resource<ArtistTopTracksResponse>>
        get() = _artistTopTracks

    fun getArtistTopTracks(artist: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getArtistTopTracks(artist)
            _artistTopTracks.postValue(listResult)
        }
    }

    private val _artistTopAlbums = MutableLiveData<Resource<ArtistTopAlbumsResponse>>()
    val artistTopAlbums: LiveData<Resource<ArtistTopAlbumsResponse>>
        get() = _artistTopAlbums

    fun getArtistTopAlbums(artist: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getArtistTopAlbums(artist)
            _artistTopAlbums.postValue(listResult)
        }
    }

}