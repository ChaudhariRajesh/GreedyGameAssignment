package com.example.greedygameassignment.utility

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class GenreNameProviderTest {

    @Test
    fun getGenreName_expected_pass() {
        GenreNameProvider.setGenreName("GreedyGame")
        assertEquals("GreedyGame", GenreNameProvider.getGenreName())
    }

}