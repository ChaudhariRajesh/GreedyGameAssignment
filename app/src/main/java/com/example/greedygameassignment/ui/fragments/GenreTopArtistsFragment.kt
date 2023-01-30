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
import com.example.greedygameassignment.ui.ArtistInfoActivity
import com.example.greedygameassignment.databinding.FragmentArtistBinding
import com.example.greedygameassignment.api.model.GenreTopArtistsResponse
import com.example.greedygameassignment.ui.adapters.GenreTopAlbumsArtistsTracksGenericRecyclerAdapter
import com.example.greedygameassignment.ui.adapters.SimpleRecyclerBindingInterface
import com.example.greedygameassignment.utility.GenreNameProvider
import com.example.greedygameassignment.utility.ImageProvider
import com.example.greedygameassignment.viewmodels.FragmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
Fragment class for the GenreTopArtist
*/

@AndroidEntryPoint
class GenreTopArtistsFragment : Fragment() {

    //Data binding
    private var _artistBinding: FragmentArtistBinding? = null
    private val artistBinding get() = _artistBinding!!

    //Stores the genre name
    private lateinit var genreName : String

    //ViewModel object
    private val viewModel : FragmentsViewModel by viewModels()

    //Stores the object list of Artists
    private lateinit var artistList : GenreTopArtistsResponse

    //Instance of generic recycler adapter for this class
    private lateinit var adapterForArtists : GenreTopAlbumsArtistsTracksGenericRecyclerAdapter<GenreTopArtistsResponse>

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
        _artistBinding = FragmentArtistBinding.inflate(inflater, container, false)
        val view = artistBinding.root

        artistBinding.artistFragmentRecyclerView.setHasFixedSize(true)
        artistBinding.artistFragmentRecyclerView.layoutManager = GridLayoutManager(context, 2)

        //Bind the views to the recycler view using the interface
        val bindingInterface = object : SimpleRecyclerBindingInterface<GenreTopArtistsResponse> {
            override fun bindData(item: GenreTopArtistsResponse, position: Int, view: View) {

                val artistNameTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemTitle)
                val artistImageView : ImageView = view.findViewById(R.id.cardViewCardItemImageView)
                val artistNameSubtitle : TextView = view.findViewById(R.id.cardViewCardItemSubTitle)

                artistNameSubtitle.visibility = View.GONE
                artistNameTextView.text = item.topartists.artist[position].name

                ImageProvider.setImageFromUrl(inflater.context, item.topartists.artist[position].image[0].text,
                    R.drawable.artist, artistImageView)
            }
        }

        //Call the viewModel method to get the top artists list of given genre
        viewModel.getGenreTopArtists(genreName)

        //Observe the live data instance
        viewModel.genreTopArtists.observe(viewLifecycleOwner, Observer { it ->
            when(it)
            {
                is Resource.Loading -> {
                    Toast.makeText(context, "Getting Data", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    it.data?.let { it2 -> artistList = it2 }

                    adapterForArtists = GenreTopAlbumsArtistsTracksGenericRecyclerAdapter<GenreTopArtistsResponse>(artistList, artistList.topartists.artist.size,
                        R.layout.layout_card_item, bindingInterface)
                    artistBinding.artistFragmentRecyclerView.adapter = adapterForArtists


                    adapterForArtists.onItemClicked = { it2, position ->
                        val newIntent = Intent(context, ArtistInfoActivity::class.java)
                        val artistName = it2.topartists.artist[position].name
                        newIntent.putExtra("artistName", artistName)
                        startActivity(newIntent)
                    }

                }
                is Resource.Error -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _artistBinding = null
    }
}