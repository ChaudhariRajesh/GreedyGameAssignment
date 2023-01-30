package com.example.greedygameassignment.repositories

import com.example.greedygameassignment.api.ApiService
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.GenreListResponse
import com.example.greedygameassignment.getOrAwaitValue
import com.example.greedygameassignment.viewmodels.MainViewModel
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GenreRepositoryTest {

    @Mock
    lateinit var apiService: ApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test_getGenreList() = runTest {

        val obj = Response.error<GenreListResponse>(401, "Unauthorized".toResponseBody())

        Mockito.`when`(apiService.getGenreList()).thenReturn(obj)

        val sut = GenreRepository(apiService)
        sut.getGenreList()
        val result = sut.getGenreList()
        assertEquals(obj, result.errorMessage)
    }

    @After
    fun tearDown() {
    }
}