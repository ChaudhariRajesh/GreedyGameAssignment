package com.example.greedygameassignment.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.databinding.FragmentTrackBinding
import com.example.greedygameassignment.api.model.GenreTopTracksResponse
import com.example.greedygameassignment.ui.ArtistInfoActivity
import com.example.greedygameassignment.ui.TrackInfoActivity
import com.example.greedygameassignment.ui.adapters.GenreTopAlbumsArtistsTracksGenericRecyclerAdapter
import com.example.greedygameassignment.ui.adapters.SimpleRecyclerBindingInterface
import com.example.greedygameassignment.utility.GenreNameProvider
import com.example.greedygameassignment.utility.ImageProvider
import com.example.greedygameassignment.viewmodels.FragmentsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/*
Fragment class for the GenreTopTracks
*/

@AndroidEntryPoint
class GenreTopTracksFragment : Fragment() {

    //Data Binding
    private var _trackBinding: FragmentTrackBinding? = null
    private val trackBinding get() = _trackBinding!!

    //Stores the genre name
    private lateinit var genreName : String

    //ViewModel object
    private val viewModel : FragmentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get the genre name from an object
        genreName = GenreNameProvider.getGenreName()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _trackBinding = FragmentTrackBinding.inflate(inflater, container, false)
        val view = trackBinding.root

        trackBinding.trackFragmentRecyclerView.setHasFixedSize(true)
        trackBinding.trackFragmentRecyclerView.layoutManager = GridLayoutManager(context, 2)

        //Bind the views to the recycler view using the interface
        val bindingInterface = object : SimpleRecyclerBindingInterface<GenreTopTracksResponse> {
            override fun bindData(item: GenreTopTracksResponse, position: Int, view: View) {

                val trackTitleTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemTitle)
                val trackArtistTextView : TextView = view.findViewById(R.id.cardViewCardItemSubTitle)
                val trackImageView : ImageView = view.findViewById(R.id.cardViewCardItemImageView)

                trackTitleTextView.text = item.tracks.track[position].name
                trackArtistTextView.text = item.tracks.track[position].artist.name

                ImageProvider.setImageFromUrl(inflater.context, item.tracks.track[position].image[1].text,
                    R.drawable.track, trackImageView)

            }

        }

        //Call the viewModel method to get the tracks list of given genre
        viewModel.getGenreTopTracks(genreName)

        //Observe the live data instance
        viewModel.genreTopTracks.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                is Resource.Loading -> {
                    trackBinding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    trackBinding.progressBar.visibility = View.INVISIBLE

                    val adapterForTracks = GenreTopAlbumsArtistsTracksGenericRecyclerAdapter<GenreTopTracksResponse>(it.data!!, it.data.tracks.track.size,
                        R.layout.layout_card_item, bindingInterface)
                    trackBinding.trackFragmentRecyclerView.adapter = adapterForTracks

                    adapterForTracks.onItemClicked = { it2, position ->
                        val newIntent = Intent(context, TrackInfoActivity::class.java)
                        val artistName = it2.tracks.track[position].artist.name
                        val trackName = it2.tracks.track[position].name
                        newIntent.putExtra("artistName", artistName)
                        newIntent.putExtra("trackName", trackName)
                        startActivity(newIntent)
                    }

                }
                is Resource.Error -> {
                    Snackbar.make(trackBinding.root, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Fixed it", View.OnClickListener { viewModel.getGenreTopTracks(genreName) }).show()
                }
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _trackBinding = null
    }

}