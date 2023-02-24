package com.example.greedygameassignment.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.ui.adapters.GenreListRecyclerAdapter
import com.example.greedygameassignment.databinding.ActivityMainBinding
import com.example.greedygameassignment.api.model.GenreListResponse
import com.example.greedygameassignment.ui.adapters.GenreTopAlbumsArtistsTracksGenericRecyclerAdapter
import com.example.greedygameassignment.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/*
Entry point for the application
Main Activity class of the project
*/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Data Binding
    private lateinit var genreListBinding: ActivityMainBinding

    //Genre list recycler adapter
    private lateinit var genreListAdapter: GenreListRecyclerAdapter

    //ViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreListBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(genreListBinding.root)

        //Set the size of the recycler adapter to true
        genreListBinding.genreListRecyclerView.setHasFixedSize(true)

        //Set the layout manager for the recycler view
        genreListBinding.genreListRecyclerView.layoutManager = GridLayoutManager(this, 3)

        //Call the viewModel method to get the genre list
        mainViewModel.getGenreList()


        //Observe the live data
        mainViewModel.genreList.observe(this, {
            when (it) {
                is Resource.Loading -> {
//                    Toast.makeText(this, "Getting Data", Toast.LENGTH_LONG).show()
                    //Show the progress bar while getting data
                    genreListBinding.progressBar.visibility = View.VISIBLE
                    //Hide the expand button while getting data
                    genreListBinding.genreListExpandButton.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    //Make the progress bar invisible once data is available
                    genreListBinding.progressBar.visibility = View.INVISIBLE
                    //Make the button visible once data is available
                    genreListBinding.genreListExpandButton.visibility = View.VISIBLE

                    genreListAdapter = GenreListRecyclerAdapter(it.data!!, 0, 9)
                    genreListBinding.genreListRecyclerView.adapter = genreListAdapter
                    addListeners()

                    //Add the click listener on the expand button
                    //When the button is clicked, complete list of genres is displayed and the button is removed from the view
                    genreListBinding.genreListExpandButton.setOnClickListener { it2 ->
                        genreListBinding.genreListExpandButton.visibility = View.INVISIBLE
                        genreListAdapter = GenreListRecyclerAdapter(it.data, 0, it.data.toptags.tag.size)
                        genreListBinding.genreListRecyclerView.adapter = genreListAdapter
                        addListeners()
                    }
                }
                is Resource.Error -> {
                    genreListBinding.genreListExpandButton.visibility = View.INVISIBLE
                    Snackbar.make(genreListBinding.root, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Fixed it", View.OnClickListener { mainViewModel.getGenreList() }).show()
                }
            }

        })


    }

    //Adds the listener on the recycler items
    private fun addListeners() {
        genreListAdapter.onItemClicked = { it2 ->
            val newIntent = Intent(this@MainActivity, GenreInfoActivity::class.java)
            val genreName = it2.name
            newIntent.putExtra("genreName", genreName)
            startActivity(newIntent)
        }
    }
}