package com.example.greedygameassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.greedygameassignment.ui.adapters.FragmentAdapter
import com.example.greedygameassignment.databinding.ActivityGenreInfoBinding
import com.example.greedygameassignment.utility.GenreNameProvider
import com.example.greedygameassignment.viewmodels.GenreInfoViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

/*
Activity to display the Genre Information
*/

@AndroidEntryPoint
class GenreInfoActivity : AppCompatActivity() {

    //Data Binding
    private lateinit var genreInfoBinding : ActivityGenreInfoBinding

    //ViewModel
    private val mainViewModel : GenreInfoViewModel by viewModels()

    //Fragment adapter
    private lateinit var fragmentAdapter : FragmentAdapter

    //Stores the genre name
    private lateinit var genreName : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreInfoBinding = ActivityGenreInfoBinding.inflate(layoutInflater)
        setContentView(genreInfoBinding.root)

        //Provide the lifecycleowner to the data binding class
        genreInfoBinding.lifecycleOwner = this


        //Provide the viewModel class to data binding class to display the data
        genreInfoBinding.viewmodel = mainViewModel

        //Get the genre name from the previous activity
        genreName = intent.getStringExtra("genreName").toString()

        //Set the genre name in the object to be used by the fragments
        GenreNameProvider.setGenreName(genreName)

        //Add the tabs to the tab layout
        genreInfoBinding.tabLayout.addTab(genreInfoBinding.tabLayout.newTab().setText("Albums"))
        genreInfoBinding.tabLayout.addTab(genreInfoBinding.tabLayout.newTab().setText("Artists"))
        genreInfoBinding.tabLayout.addTab(genreInfoBinding.tabLayout.newTab().setText("Tracks"))

        //Set the fragment adapter
        fragmentAdapter = FragmentAdapter(this.supportFragmentManager, this.lifecycle)
        genreInfoBinding.viewPager2.adapter = fragmentAdapter

        //Implementing the listeners on tab layout to set the corresponding page
        genreInfoBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                genreInfoBinding.viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        //Implementing the callbacks on the viewpager to display the corresponding tab
        genreInfoBinding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                genreInfoBinding.tabLayout.selectTab(genreInfoBinding.tabLayout.getTabAt(position))
            }
        })

        //Call the viewModel method to get genre information. It will be set automatically to the views using data binding
        mainViewModel.getGenreInfo(genreName)
    }

}