package com.example.greedygameassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.databinding.ActivityAlbumInfoBinding
import com.example.greedygameassignment.api.model.AlbumInfoResponse
import com.example.greedygameassignment.utility.ImageProvider
import com.example.greedygameassignment.viewmodels.AlbumInfoViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

/*
Activity to display the Album Information
*/

@AndroidEntryPoint
class AlbumInfoActivity : AppCompatActivity() {

    //Data Binding
    private lateinit var albumInfoBinding : ActivityAlbumInfoBinding

    //Stores the artist name
    private lateinit var artistName : String

    //Stores the album name
    private lateinit var albumName : String

    private val mainViewModel : AlbumInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumInfoBinding = ActivityAlbumInfoBinding.inflate(layoutInflater)
        setContentView(albumInfoBinding.root)

        //Get the artist name and album name from the previous activity
        artistName = intent.getStringExtra("artistName").toString()
        albumName = intent.getStringExtra("albumName").toString()

        //Provide the lifecycleowner to the data binding class
        albumInfoBinding.lifecycleOwner = this

        //Provide the viewModel class to data binding class to display the data
        albumInfoBinding.viewmodel = mainViewModel

        //Call the viewModel method to get album information
        mainViewModel.getAlbumInfo(artistName, albumName)

        //Observe the live data
        mainViewModel.albumInfo.observe(this, {
            when (it)
            {
                is Resource.Loading -> {
                    Toast.makeText(this, "Getting Data", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    it.data?.let { it2 ->
                        //Set the data to corresponding views
                        setData(it2)
                    }
                }
                is Resource.Error -> {
                    it.errorMessage
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setData(albumInfo : AlbumInfoResponse)
    {
        //Set the image to the provided image view using the static class object
        ImageProvider.setImageFromUrl(this, albumInfo.album.image[2].text,
            R.drawable.album_banner, albumInfoBinding.AlbumDetailsImageView)

        //Add the chips (to show tags) to the horizontal list view and add the click listener on it
        for (element in albumInfo.album.tags.tag)
        {
            val newChip = Chip(this)
            newChip.text = element.name
            albumInfoBinding.albumDetailsChipGroup.addView(newChip)

            newChip.setOnClickListener{
                //When the chip (tag / genre) is clicked, its information is displayed in a new activity
                val newIntent = Intent(this, GenreInfoActivity::class.java)
                val genreName = albumInfoBinding.albumDetailsChipGroup.findViewById<Chip>(it.id).text
                newIntent.putExtra("genreName", genreName)
                startActivity(newIntent)
                finish()
            }
        }

    }

}