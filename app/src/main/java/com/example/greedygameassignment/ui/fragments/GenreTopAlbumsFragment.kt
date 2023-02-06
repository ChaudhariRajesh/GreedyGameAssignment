package com.example.greedygameassignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greedygameassignment.api.model.GenreTopAlbumsResponse
import android.content.Intent
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.Resource
import com.example.greedygameassignment.ui.AlbumInfoActivity
import com.example.greedygameassignment.databinding.FragmentAlbumBinding
import com.example.greedygameassignment.ui.adapters.GenreTopAlbumsArtistsTracksGenericRecyclerAdapter
import com.example.greedygameassignment.ui.adapters.SimpleRecyclerBindingInterface
import com.example.greedygameassignment.utility.GenreNameProvider
import com.example.greedygameassignment.utility.ImageProvider
import com.example.greedygameassignment.viewmodels.FragmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
Fragment class for the GenreTopAlbums
*/

@AndroidEntryPoint
class GenreTopAlbumsFragment : Fragment() {

    //Data Binding
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    //Stores the name of the genre
    private lateinit var genreName : String

    //ViewModel object
    private val viewModel : FragmentsViewModel by viewModels()

//    //Stores the object list of albums
//    private lateinit var albumList : GenreTopAlbumsResponse

    //Instance of generic recycler adapter for this fragment
    private lateinit var adapterForAlbums : GenreTopAlbumsArtistsTracksGenericRecyclerAdapter<GenreTopAlbumsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get the genre name from an object
        genreName = GenreNameProvider.getGenreName()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.albumFragmentRecyclerView.setHasFixedSize(true)
        binding.albumFragmentRecyclerView.layoutManager = GridLayoutManager(context, 2)

        //Bind the views to the recycler view using the interface
        val bindingInterface = object : SimpleRecyclerBindingInterface<GenreTopAlbumsResponse>{
            override fun bindData(item: GenreTopAlbumsResponse, position: Int, view: View) {
                val albumTitleTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemTitle)
                val albumArtistNameTextView : TextView = view.findViewById<TextView>(R.id.cardViewCardItemSubTitle)
                val albumImage : ImageView = view.findViewById<ImageView>(R.id.cardViewCardItemImageView)

                albumTitleTextView.text = item.albums.album[position].name
                albumArtistNameTextView.text = item.albums.album[position].artist.name

                ImageProvider.setImageFromUrl(inflater.context, item.albums.album[position].image[1].text,
                    R.drawable.album, albumImage)
            }
        }

        //Call the viewModel method to get the album list of given genre
        viewModel.getGenreTopAlbums(genreName)

        //Observe the live data of album
        viewModel.genreTopAlbums.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                is Resource.Loading -> {
                    Toast.makeText(context, "Getting Data", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
//                    it.data?.let { it2 -> albumList = it2 }

                    adapterForAlbums = GenreTopAlbumsArtistsTracksGenericRecyclerAdapter<GenreTopAlbumsResponse>(it.data!!, it.data.albums.album.size,
                        R.layout.layout_card_item, bindingInterface)
                    binding.albumFragmentRecyclerView.adapter = adapterForAlbums

                    //Set the click listener on the recycler view items
                    adapterForAlbums.onItemClicked = { it2, position ->
                        //Start the Album Information when particular item is clicked
                        val newIntent = Intent(context, AlbumInfoActivity::class.java)
                        val artistName = it2.albums.album[position].artist.name
                        val albumName = it2.albums.album[position].name
                        newIntent.putExtra("artistName", artistName)
                        newIntent.putExtra("albumName", albumName)
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
        _binding = null
    }

}