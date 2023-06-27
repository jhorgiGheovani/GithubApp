package com.bangkit23.githubapp


import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bangkit23.githubapp.ui.activity.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun inputQuerySearch(){
        val query = "jho"
        onView(withId(R.id.search)).perform(click());  //open the searchView

        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(query));// ketik teks

        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(pressKey(KeyEvent.KEYCODE_ENTER));  // mulai mencari
    }
}