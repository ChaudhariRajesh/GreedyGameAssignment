package com.example.greedygameassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.api.model.AlbumInfoResponse
import com.example.greedygameassignment.api.model.TrackInfoResponse
import com.example.greedygameassignment.databinding.ActivityAlbumInfoBinding
import com.example.greedygameassignment.databinding.ActivityTrackInfoBinding
import com.example.greedygameassignment.utility.ImageProvider
import com.example.greedygameassignment.viewmodels.AlbumInfoViewModel
import com.example.greedygameassignment.viewmodels.TrackInfoViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackInfoActivity : AppCompatActivity() {

    //Data Binding
    private lateinit var trackInfoBinding : ActivityTrackInfoBinding

    //Stores the artist name
    private lateinit var artistName : String

    //Stores the album name
    private lateinit var trackName : String

    //ViewModel
    private val trackViewModel : TrackInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackInfoBinding = ActivityTrackInfoBinding.inflate(layoutInflater)
        setContentView(trackInfoBinding.root)

        //Get the artist name and album name from the previous activity
        artistName = intent.getStringExtra("artistName").toString()
        trackName = intent.getStringExtra("trackName").toString()

        //Provide the lifecycleowner to the data binding class
        trackInfoBinding.lifecycleOwner = this

        //Provide the viewModel class to data binding class to display the data
        trackInfoBinding.viewmodel = trackViewModel

        //Call the viewModel method to get album information
        trackViewModel.getTrackInfo(artistName, trackName)

        //Observe the live data
        trackViewModel.trackInfo.observe(this, {
            when (it)
            {
                is Resource.Loading -> {
                    trackInfoBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    trackInfoBinding.progressBar.visibility = View.INVISIBLE
                    it.data?.let { it2 ->
                        //Set the data to corresponding views
                        setData(it2)
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(trackInfoBinding.root, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Fixed it", View.OnClickListener { trackViewModel.getTrackInfo(artistName, trackName) }).show()
                }
            }
        })

    }

    private fun setData(trackInfo : TrackInfoResponse)
    {
        //Set the image to the provided image view using the static class object
        if(! trackInfo.track.album.image[2].text.isNullOrEmpty())
        {
            ImageProvider.setImageFromUrl(this, trackInfo.track.album.image[2].text,
                R.drawable.track, trackInfoBinding.trackDetailsImageView)
        }

        //Add the chips (to show tags) to the horizontal list view and add the click listener on it
        for (element in trackInfo.track.toptags.tag)
        {
            val newChip = Chip(this)
            newChip.text = element.name
            trackInfoBinding.albumDetailsChipGroup.addView(newChip)

            newChip.setOnClickListener{
                //When the chip (tag / genre) is clicked, its information is displayed in a new activity
                val newIntent = Intent(this, GenreInfoActivity::class.java)
                val genreName = trackInfoBinding.albumDetailsChipGroup.findViewById<Chip>(it.id).text
                newIntent.putExtra("genreName", genreName)
                startActivity(newIntent)
                finish()
            }
        }

    }

}