package com.example.greedygameassignment.api

//Generic Response class, provides output of the api responses in one of the three types

sealed class Resource<T>(val data : T? = null, val errorMessage : String? = null)
{
    class Loading<T>(data : T? = null): Resource<T>(data = data)
    class Success<T>(data : T) : Resource<T>(data = data)
    class Error<T>(errorMessage : String?) : Resource<T>(errorMessage = errorMessage)
}
