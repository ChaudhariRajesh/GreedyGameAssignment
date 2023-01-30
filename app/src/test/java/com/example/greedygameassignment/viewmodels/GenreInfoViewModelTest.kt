package com.example.greedygameassignment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.*
import com.example.greedygameassignment.getOrAwaitValue
import com.example.greedygameassignment.repositories.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GenreInfoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository : GenreRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_getGenreInfo() = runTest {
        val obj = Resource.Error<GenreInfoResponse>("Error")
        Mockito.`when`(repository.getGenreInfo("rock")).thenReturn(obj)

        val sut = GenreInfoViewModel(repository)
        sut.getGenreInfo("rock")
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.genreInfo.getOrAwaitValue()

        assertEquals(obj, result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}