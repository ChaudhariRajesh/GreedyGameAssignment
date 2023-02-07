package com.example.greedygameassignment.repositories

import android.util.Log
import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.IllegalStateException

//Base repository class, provides a generic method to call the api methods, helps to avoid boilerplate code

open class DataRepository {

    suspend fun <T> genericApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                Resource.Loading<T>()
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Resource.Success(data = response.body()!!)
                    } else {
                        Resource.Error("No Data Found")
                    }
                } else {
                    Resource.Error(response.code().toString() + " " + response.message() ?: "Something went wrong")
                }
            } catch (e: HttpException) {
                Resource.Error(e.message ?: "There is some problem : ${e.message}")
            } catch (e: IOException) {
                Resource.Error("Please check your network connection")
            } catch(e : IllegalStateException) {
                Resource.Error("There is some problem : ${e.message}")
            }
            catch (e: Exception) {
                Resource.Error("Something went wrong : ${e.message}")
            }
        }
    }

}
