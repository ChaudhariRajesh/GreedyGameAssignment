package com.example.greedygameassignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygameassignment.api.model.Album

/*
Generic recycler adapter for ArtistTopTracks, ArtistTopAlbums
An interface need to be implemented in the Activity to bind the views
*/

class ArtistTopTracksAlbumsGenericRecyclerAdapter <T : Any>
    (private val dataList : T, private val dataListSize : Int, @LayoutRes private val layoutID : Int,
     private val bindingInterface: SimpleRecyclerBindingInterface<T>)
    : RecyclerView.Adapter<ArtistTopTracksAlbumsGenericRecyclerAdapter.SimpleViewHolder>() {


    class SimpleViewHolder (val view : View) : RecyclerView.ViewHolder(view)
    {
        fun <T : Any> bind(
            item: T,
            position: Int,
            bindingInterface: SimpleRecyclerBindingInterface<T>
        ) = bindingInterface.bindData(item, position, view)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistTopTracksAlbumsGenericRecyclerAdapter.SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)

        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistTopTracksAlbumsGenericRecyclerAdapter.SimpleViewHolder, position: Int) {

        val item = dataList
        holder.bind(item, position, bindingInterface)
    }

    override fun getItemCount(): Int {
        return dataListSize
    }

}