package com.example.greedygameassignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/*
Generic recycler adapter for GenreTopAlbums, GenreTopArtists, GenreTopTracks
*/

class GenreTopAlbumsArtistsTracksGenericRecyclerAdapter  <T : Any>
    (private val dataList : T, private val dataListSize : Int, @LayoutRes private val layoutID : Int,
     private val bindingInterface: SimpleRecyclerBindingInterface<T>)
    : RecyclerView.Adapter<GenreTopAlbumsArtistsTracksGenericRecyclerAdapter.SimpleViewHolder>() {

    var onItemClicked : ((T, position : Int) -> Unit)? = null

    class SimpleViewHolder(val view : View) : RecyclerView.ViewHolder(view)
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
    ): GenreTopAlbumsArtistsTracksGenericRecyclerAdapter.SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)

        return GenreTopAlbumsArtistsTracksGenericRecyclerAdapter.SimpleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GenreTopAlbumsArtistsTracksGenericRecyclerAdapter.SimpleViewHolder,
        position: Int
    ) {
        holder.bind(dataList, position, bindingInterface)
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(dataList, position)
        }

    }

    override fun getItemCount() = dataListSize

}