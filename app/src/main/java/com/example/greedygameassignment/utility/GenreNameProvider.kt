package com.example.greedygameassignment.utility

//Stores the genre name to be used by Fragments
object GenreNameProvider {

    private lateinit var genreName : String

    fun setGenreName(name : String)
    {
        genreName = name
    }

    fun getGenreName() : String
    {
        return genreName
    }

}