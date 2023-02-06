package com.example.greedygameassignment.utility

import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import org.w3c.dom.Text



public object BindingAdapters {

    @JvmStatic
    public fun milisecondsToTime (textView : TextView, miliseconds : String)
    {
        val time = "Duration "
        time.plus(miliseconds.toInt() / 60000).plus(" Mins ")
        time.plus(miliseconds.toInt() % 60000).plus(" Sec")
        textView.text = time
    }

//    @JvmStatic fun timeToMiliseconds(miliseconds: String) : Int
//    {
//        var mins = miliseconds.substring(9, 11).toInt() / 60
//        mins += miliseconds.substring(11).toInt()
//        return mins
//    }
}