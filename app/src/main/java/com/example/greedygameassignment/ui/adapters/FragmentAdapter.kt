package com.example.greedygameassignment.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.greedygameassignment.ui.fragments.GenreTopAlbumsFragment
import com.example.greedygameassignment.ui.fragments.GenreTopArtistsFragment
import com.example.greedygameassignment.ui.fragments.GenreTopTracksFragment

/*
Adapter class for Fragments which display GenreTopAlbums, GenreTopArtists, GenreTopTracks
*/

class FragmentAdapter(fragmentManager : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0)
            return GenreTopAlbumsFragment()
        else if(position == 1)
            return GenreTopArtistsFragment()
        return GenreTopTracksFragment()
    }

}