package com.example.greedygameassignment.utility

import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
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