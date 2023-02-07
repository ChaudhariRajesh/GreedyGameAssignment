package com.example.greedygameassignment.utility

import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.example.greedygameassignment.R
import com.example.greedygameassignment.api.model.TrackInfoResponse
import com.example.greedygameassignment.ui.GenreInfoActivity
import com.google.android.material.chip.Chip
import org.w3c.dom.Text



public object BindingAdapters {

    @JvmStatic
    public fun milisecondsToTime (miliseconds : String?) : String
    {
        miliseconds?.let {
            return StringBuilder().append("Duration: ").append(it.toInt().div(60000)).append(" Mins ").append((it.toInt().div(1000)).rem(60)).append(" Sec ").toString()
        }
        return "Duration: Unknown"
    }

}