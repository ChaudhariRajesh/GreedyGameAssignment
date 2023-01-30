package com.example.greedygameassignment.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.*
import com.example.greedygameassignment.repositories.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel for the GenreRepository

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: GenreRepository) : ViewModel() {


    private val _genreList = MutableLiveData<Resource<GenreListResponse>>()
    val genreList: LiveData<Resource<GenreListResponse>>
        get() = _genreList

//    fun getGenreList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _genreList.postValue(Resource.Loading())
//            try {
//                val listResult = repository.getGenreList()
//                _genreList.postValue(Resource.Success(listResult))
//            } catch (e: Exception) {
//                _genreList.postValue(Resource.Error(e.message))
//            }
//        }
//    }


    fun getGenreList() {
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = repository.getGenreList()
            _genreList.postValue(listResult)
        }
    }

}