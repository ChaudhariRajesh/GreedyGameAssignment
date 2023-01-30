package com.example.greedygameassignment.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumptech.glide.load.engine.Resource
import com.example.greedygameassignment.api.model.GenreListResponse
import com.example.greedygameassignment.getOrAwaitValue
import com.example.greedygameassignment.repositories.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

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
    fun test_getGenreList() = runTest {
        val obj = com.example.greedygameassignment.api.Resource.Error<GenreListResponse>("Error")
        Mockito.`when`(repository.getGenreList()).thenReturn(obj)

        val sut = MainViewModel(repository)
        sut.getGenreList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.genreList.getOrAwaitValue()

        assertEquals(obj, result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}