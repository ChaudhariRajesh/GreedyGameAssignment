package com.example.greedygameassignment.ui.adapters

import android.view.View

/*
Binding interface for the activities to implement in order to bind their views in the recycler view
*/

interface SimpleRecyclerBindingInterface<T> {
    fun bindData(item: T, position: Int, view: View)
}