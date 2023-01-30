package com.example.greedygameassignment.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.GenreInfoResponse
import com.example.greedygameassignment.repositories.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel for the GenreRepository

@HiltViewModel
class GenreInfoViewModel @Inject constructor(private val repository: GenreRepository) :
    ViewModel() {

    private val _genreInfo = MutableLiveData<Resource<GenreInfoResponse>>()
    val genreInfo: LiveData<Resource<GenreInfoResponse>>
        get() = _genreInfo

    fun getGenreInfo(genreName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getGenreInfo(genreName)
            _genreInfo.postValue(listResult)
        }
    }

}
