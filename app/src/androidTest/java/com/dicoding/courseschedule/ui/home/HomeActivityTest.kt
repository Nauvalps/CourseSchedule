package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dicoding.courseschedule.R
import org.junit.Before
import org.junit.Test

class HomeActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun toAddCourseActivity() {
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.add_course)).check(matches(isDisplayed()))
        onView(withId(R.id.list_day)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_start_time)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_end_time)).check(matches(isDisplayed()))
        onView(withId(R.id.add_lecturer)).check(matches(isDisplayed()))
        onView(withId(R.id.add_note)).check(matches(isDisplayed()))
    }
}