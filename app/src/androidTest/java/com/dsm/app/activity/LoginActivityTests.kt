package com.dsm.app.activity

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dsm.app.MockServerDispatcher
import com.dsm.app.withBackground
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.ui.login.LoginActivity
import com.dsm.dsmmarketandroid.presentation.ui.main.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTests {

    @get:Rule
    val activityRule = ActivityTestRule(LoginActivity::class.java, false, false)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @Test
    fun loginSuccessTest() {
        mockWebServer.dispatcher = MockServerDispatcher.RequestDispatcher()
        activityRule.launchActivity(Intent())

        Intents.init()

        onView(withId(R.id.et_login_email)).perform(typeText("example@example.com"))
        onView(withId(R.id.et_login_password)).perform(typeText("passwordexample"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        intended(hasComponent(MainActivity::class.java.name))
        assertTrue(activityRule.activity.isFinishing)
    }

    @Test
    fun serverErrorTest() {
        mockWebServer.dispatcher = MockServerDispatcher.ErrorDispatcher()
        activityRule.launchActivity(Intent())

        onView(withId(R.id.et_login_email)).perform(typeText("example@example.com"))
        onView(withId(R.id.et_login_password)).perform(typeText("passwordexample"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withText(R.string.fail_server_error)).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
    }

    @Test
    fun invalidEmailTest() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.et_login_email)).perform(typeText("example@com"))
        onView(withId(R.id.et_login_password)).perform(typeText("passwordexample"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withText(R.string.fail_invalid_email)).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
    }

    @Test
    fun loginBtnEnableTest() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.et_login_email)).perform(typeText("example@example.com"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).check(matches(withBackground(R.drawable.bg_grey_rounded)))
        onView(withId(R.id.btn_login)).check(matches(not(isClickable())))
    }

    @Test
    fun loginBtnDisableTest() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.et_login_email)).perform(typeText("example@example.com"))
        onView(withId(R.id.et_login_password)).perform(typeText("passwordexample"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).check(matches(withBackground(R.drawable.bg_primary_rounded)))
        onView(withId(R.id.btn_login)).check(matches(isClickable()))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}