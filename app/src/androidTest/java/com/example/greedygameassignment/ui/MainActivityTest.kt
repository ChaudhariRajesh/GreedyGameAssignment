package com.example.greedygameassignment.ui

import android.widget.Button
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.greedygameassignment.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testGetMoreGenreButton_expectedInsivible()
    {
        onView(withId(R.id.genreListExpandButton)).perform(click())
        onView(withId(R.id.genreListExpandButton)).check(matches(isDisplayed()))
    }


}