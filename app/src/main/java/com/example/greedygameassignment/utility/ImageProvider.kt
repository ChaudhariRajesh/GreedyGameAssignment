package com.example.greedygameassignment.utility

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide


//Sets the image to the provided imageview using the Glide library
object ImageProvider {

    fun setImageFromUrl(context: Context, url: String, placeholder: Int, target: ImageView){
        Glide.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(target)
    }

}