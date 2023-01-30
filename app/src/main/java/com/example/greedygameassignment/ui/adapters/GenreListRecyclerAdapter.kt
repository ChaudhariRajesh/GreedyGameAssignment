package com.example.greedygameassignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.model.GenreListResponse
import com.example.greedygameassignment.api.model.Tag

/*
Recycler adapter for GenreList
*/

class GenreListRecyclerAdapter(private val genreList : GenreListResponse, private var start : Int, private var end : Int) :
    RecyclerView.Adapter<GenreListRecyclerAdapter.GenreListHolder>() {

    var onItemClicked : ((Tag) -> Unit)? = null

    class GenreListHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val genreNameTextView : TextView = itemView.findViewById<TextView>(R.id.cardViewGenreListItemTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_genre_list_item, parent, false)
        return GenreListHolder(view)
    }

    override fun onBindViewHolder(holder: GenreListHolder, position: Int) {
        var genre = genreList.toptags.tag[position]
        holder.genreNameTextView.text = genre.name
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(genre)
        }
    }


    override fun getItemCount(): Int {
        if(end < genreList.toptags.tag.size)
            return (end - start + 1)
        return (end - start)
    }
}