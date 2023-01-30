package com.example.greedygameassignment

import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.model.Attr
import com.example.greedygameassignment.api.model.GenreListResponse
import com.example.greedygameassignment.api.model.Tag
import com.example.greedygameassignment.api.model.Toptags
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicApiTest {

    lateinit var mockWebServer : MockWebServer
    lateinit var apiService: ApiService


    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Test
    fun test_getGenreList() = runTest {

        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getGenreList()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() != null)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}