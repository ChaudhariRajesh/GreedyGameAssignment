package com.example.greedygameassignment.viewmodels

import androidx.lifecycle.*
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.AlbumInfoResponse
import com.example.greedygameassignment.repositories.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel for the AlbumRepository

@HiltViewModel
class AlbumInfoViewModel @Inject constructor ( private val repository: AlbumRepository ): ViewModel() {

    private val _albumInfo = MutableLiveData<Resource<AlbumInfoResponse>>()
    val albumInfo: LiveData<Resource<AlbumInfoResponse>>
        get() = _albumInfo

    fun getAlbumInfo(artist: String, album: String) {
        viewModelScope.launch(Dispatchers.IO) {
                val listResult = repository.getAlbumInfo(artist, album)
                _albumInfo.postValue(listResult)
        }
    }

}