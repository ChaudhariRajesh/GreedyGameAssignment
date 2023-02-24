package com.example.greedygameassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.databinding.ActivityArtistInfoBinding
import com.example.greedygameassignment.api.model.ArtistInfoResponse
import com.example.greedygameassignment.api.model.ArtistTopAlbumsResponse
import com.example.greedygameassignment.api.model.ArtistTopTracksResponse
import com.example.greedygameassignment.ui.adapters.ArtistTopTracksAlbumsGenericRecyclerAdapter
import com.example.greedygameassignment.ui.adapters.SimpleRecyclerBindingInterface
import com.example.greedygameassignment.utility.ImageProvider
import com.example.greedygameassignment.viewmodels.ArtistInfoViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

/*
Activity to display the Artist Information
*/


@AndroidEntryPoint
class ArtistInfoActivity : AppCompatActivity() {

    //Data Binding
    private lateinit var artistInfoBinding : ActivityArtistInfoBinding

    //ViewModel
    private val mainViewModel : ArtistInfoViewModel by viewModels()

    //Stores the artist name
    private lateinit var artistName : String

    //Generic recycler adapters for tracks and album list
    private lateinit var adapterForTracks : ArtistTopTracksAlbumsGenericRecyclerAdapter<ArtistTopTracksResponse>
    private lateinit var adapterForAlbums : ArtistTopTracksAlbumsGenericRecyclerAdapter<ArtistTopAlbumsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistInfoBinding = ActivityArtistInfoBinding.inflate(layoutInflater)
        setContentView(artistInfoBinding.root)

        //Provide the lifecycleowner to the data binding class
        artistInfoBinding.lifecycleOwner = this

        //Provide the viewModel class to data binding class to display the data
        artistInfoBinding.viewmodel = mainViewModel

        //Get the artist name from the previous activity
        artistName = intent.getStringExtra("artistName").toString()

        //Set the layouts for the recycler view to display contents
        artistInfoBinding.artistDetailsTopTracksRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        artistInfoBinding.artistDetailsTopAlbumsRecyclerView.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)

        //Call the viewModel method to get artist information
        mainViewModel.getArtistInfo(artistName)

        //Observe the live data
        mainViewModel.artistInfo.observe(this, {
            when(it)
            {
                is Resource.Loading -> {
                    artistInfoBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    artistInfoBinding.progressBar.visibility = View.INVISIBLE
                    setData(it.data!!)
                    ImageProvider.setImageFromUrl(this, it.data.artist.image[1].text,
                        R.drawable.artist_banner, artistInfoBinding.artistInfoImageView)
                }
                is Resource.Error -> {
                    Snackbar.make(artistInfoBinding.root, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Fixed it", View.OnClickListener { mainViewModel.getArtistInfo(artistName) }).show()
                }
            }
        })


        //Bind the views to the recycler view using the interface
        val bindingInterfaceTracks = object : SimpleRecyclerBindingInterface<ArtistTopTracksResponse>
        {
            override fun bindData (item: ArtistTopTracksResponse, position: Int, view: View)
            {

                val cardView : CardView = view.findViewById(R.id.cardViewCardItem)
                val trackTitleTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemTitle)
                val trackArtistNameTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemSubTitle)
                val trackImage : ImageView = view.findViewById<ImageView>(R.id.cardViewCardItemImageView)

                val widthInDp = (150 * (application.resources.displayMetrics.density)).roundToInt()
                val heightInDp = (150 * (application.resources.displayMetrics.density)).roundToInt()
                cardView.layoutParams = FrameLayout.LayoutParams(widthInDp, heightInDp)

                trackImage.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

                trackTitleTextView.text = item.toptracks.track[position].name
                trackArtistNameTextView.text = item.toptracks.track[position].artist.name
                if(item.toptracks.track[position].image.isNotEmpty()) {
                    ImageProvider.setImageFromUrl(this@ArtistInfoActivity, item.toptracks.track[position].image[0].text, R.drawable.track, trackImage)
                }

            }
        }

        //Call the viewModel method to get tracks list
        mainViewModel.getArtistTopTracks(artistName)

        //Observe the live data
        mainViewModel.artistTopTracks.observe(this, {
            when(it)
            {
                is Resource.Loading -> {
                    artistInfoBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {

                    artistInfoBinding.progressBar.visibility = View.INVISIBLE

                    adapterForTracks = ArtistTopTracksAlbumsGenericRecyclerAdapter<ArtistTopTracksResponse>(it.data!!, it.data.toptracks.track.size,
                        R.layout.layout_card_item, bindingInterfaceTracks)
                    artistInfoBinding.artistDetailsTopTracksRecyclerView.adapter = adapterForTracks

                    adapterForTracks.onItemClicked = { it2, position ->
                        val newIntent = Intent(this, TrackInfoActivity::class.java)
                        val artistName = it2.toptracks.track[position].artist.name
                        val trackName = it2.toptracks.track[position].name
                        newIntent.putExtra("artistName", artistName)
                        newIntent.putExtra("trackName", trackName)
                        startActivity(newIntent)
                    }

                }
                is Resource.Error -> {
                    Snackbar.make(artistInfoBinding.root, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Fixed it", View.OnClickListener { mainViewModel.getArtistTopTracks(artistName) }).show()
                }
            }

        })


        //Bind the views to the recycler view using the interface
        val bindingInterfaceAlbums = object : SimpleRecyclerBindingInterface<ArtistTopAlbumsResponse>
        {
            override fun bindData (item: ArtistTopAlbumsResponse, position: Int, view: View)
            {

                val cardView : CardView = view.findViewById(R.id.cardViewCardItem)
                val albumTitleTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemTitle)
                val albumArtistNameTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemSubTitle)
                val albumImage : ImageView = view.findViewById<ImageView>(R.id.cardViewCardItemImageView)

                val widthInDp = (150 * (application.resources.displayMetrics.density)).roundToInt()
                val heightInDp = (150 * (application.resources.displayMetrics.density)).roundToInt()
                cardView.layoutParams = FrameLayout.LayoutParams(widthInDp, heightInDp)

                albumImage.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

                albumTitleTextView.text = item.topalbums.album[position].name
                albumArtistNameTextView.text = item.topalbums.album[position].artist.name
                if(item.topalbums.album[position].image.isNotEmpty()) {
                    ImageProvider.setImageFromUrl(this@ArtistInfoActivity, item.topalbums.album[position].image[0].text, R.drawable.album, albumImage)
                }

            }
        }


        //Call the viewModel method to get album list
        mainViewModel.getArtistTopAlbums(artistName)
        mainViewModel.artistTopAlbums.observe(this, {
            when(it)
            {
                is Resource.Loading -> {
                    artistInfoBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    artistInfoBinding.progressBar.visibility = View.INVISIBLE

                    adapterForAlbums = ArtistTopTracksAlbumsGenericRecyclerAdapter<ArtistTopAlbumsResponse>(it.data!!, it.data.topalbums.album.size,
                        R.layout.layout_card_item, bindingInterfaceAlbums)
                    artistInfoBinding.artistDetailsTopAlbumsRecyclerView.adapter = adapterForAlbums

                    adapterForAlbums.onItemClicked = { it2, position ->
                        val newIntent = Intent(this, AlbumInfoActivity::class.java)
                        val artistName = it2.topalbums.album[position].artist.name
                        val albumName = it2.topalbums.album[position].name
                        newIntent.putExtra("artistName", artistName)
                        newIntent.putExtra("albumName", albumName)
                        startActivity(newIntent)
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(artistInfoBinding.root, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Fixed it", View.OnClickListener { mainViewModel.getArtistTopAlbums(artistName) }).show()
                }
            }

        })

    }

    private fun setData(artistInfo : ArtistInfoResponse)
    {
        //Set the image to the provided image view using the static class object
        for (element in artistInfo.artist.tags.tag)
        {
            //Add the chips (to show tags) to the horizontal list view and add the click listener on it
            val newChip = Chip(this)
            newChip.text = element.name
            artistInfoBinding.albumDetailsChipGroup.addView(newChip)

            newChip.setOnClickListener{
                //When the chip (tag / genre) is clicked, its information is displayed in a new activity
                val newIntent = Intent(this, GenreInfoActivity::class.java)
                val genreName = artistInfoBinding.albumDetailsChipGroup.findViewById<Chip>(it.id).text
                newIntent.putExtra("genreName", genreName)
                startActivity(newIntent)
                finish()
            }
        }
    }

}