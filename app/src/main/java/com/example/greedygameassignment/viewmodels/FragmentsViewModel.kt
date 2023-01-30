package com.example.greedygameassignment.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.GenreTopAlbumsResponse
import com.example.greedygameassignment.api.model.GenreTopArtistsResponse
import com.example.greedygameassignment.api.model.GenreTopTracksResponse
import com.example.greedygameassignment.repositories.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel for the GenreRepository

@HiltViewModel
class FragmentsViewModel @Inject constructor(private val repository: GenreRepository) :
    ViewModel() {

    private val _genreTopAlbums = MutableLiveData<Resource<GenreTopAlbumsResponse>>()
    val genreTopAlbums: LiveData<Resource<GenreTopAlbumsResponse>>
        get() = _genreTopAlbums

    fun getGenreTopAlbums(genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _genreTopAlbums.postValue(Resource.Loading())
            val listResult = repository.getGenreTopAlbums(genreName)
            _genreTopAlbums.postValue(listResult)
        }
    }


    private val _genreTopArtists = MutableLiveData<Resource<GenreTopArtistsResponse>>()
    val genreTopArtists: LiveData<Resource<GenreTopArtistsResponse>>
        get() = _genreTopArtists

    fun getGenreTopArtists(genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getGenreTopArtists(genreName)
            _genreTopArtists.postValue(listResult)
        }
    }

    private val _genreTopTracks = MutableLiveData<Resource<GenreTopTracksResponse>>()
    val genreTopTracks: LiveData<Resource<GenreTopTracksResponse>>
        get() = _genreTopTracks

    fun getGenreTopTracks(genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getGenreTopTracks(genreName)
            _genreTopTracks.postValue(listResult)
        }
    }

}