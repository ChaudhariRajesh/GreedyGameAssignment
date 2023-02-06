package com.example.greedygameassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.TrackInfoResponse
import com.example.greedygameassignment.repositories.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackInfoViewModel @Inject constructor (private val repository: TrackRepository): ViewModel() {

    private val _trackInfo = MutableLiveData<Resource<TrackInfoResponse>>()
    val trackInfo: LiveData<Resource<TrackInfoResponse>>
        get() = _trackInfo

    fun getTrackInfo(artist: String, track: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getTrackInfo(artist, track)
            _trackInfo.postValue(listResult)
        }
    }

}