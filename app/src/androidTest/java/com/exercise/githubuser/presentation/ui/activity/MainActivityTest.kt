package com.exercise.githubuser.presentation.ui.activity

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.exercise.githubuser.R
import com.exercise.githubuser.data.entity.UserDetail
import com.exercise.githubuser.ext.convertInputStreamToString
import com.exercise.githubuser.presentation.ui.adapter.UserAdapter
import com.exercise.githubuser.presentation.ui.fragment.UserFragment
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    private var jsonBody = ""
    private var jsonDetailBody = ""
    private var mockWebServer = MockWebServer()
    private lateinit var userDetail: UserDetail

    @Before
    fun setup() {

        val jsonInputStream = javaClass.getResourceAsStream("/user.json")
        jsonInputStream?.let {
            jsonBody = String().convertInputStreamToString(it)
        }

        val jsonDetailInputStream = javaClass.getResourceAsStream("/userdetail.json")
        jsonDetailInputStream?.let {
            jsonDetailBody = String().convertInputStreamToString(it)
        }
        userDetail = UserDetail.parseUserDetail(jsonDetailBody)

        mockWebServer.start(8080)
        mockWebServer.dispatcher = getDispatcher()

        activityRule.activity.apply {
            runOnUiThread {
                changeFragment(
                    UserFragment(),
                    activityRule.activity.supportFragmentManager,
                    false
                )
            }
        }
    }

    @Test
    fun testChangeFragment() {
        SystemClock.sleep(1000)
        onView(withId(R.id.recyclerViewUser)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerViewUser)).check(
            matches(
                hasChildCount(8)
            )
        )
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(2, click()))
        SystemClock.sleep(1000)
        onView(withId(R.id.imageViewUserDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDetailBio)).check(
            matches(
                withText(userDetail.bio)
            )
        )
        onView(withId(R.id.textViewName)).check(
            matches(
                withText(userDetail.name)
            )
        )
        onView(withId(R.id.textViewDetailLink)).check(
            matches(
                withText(userDetail.blog)
            )
        )
        onView(withId(R.id.textViewDetailLogin)).check(
            matches(
                withText(userDetail.login)
            )
        )
    }

    private fun getDispatcher(): Dispatcher {
        return object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path == "/users?&since=0&per_page=20") {
                    return MockResponse().setResponseCode(200)
                        .setBody(jsonBody)
                }
                if (request.path == "/users/nitay") {
                    return MockResponse().setResponseCode(200)
                        .setBody(jsonDetailBody)
                }
                throw IllegalStateException("no mock set up for " + request.path!!)
            }
        }
    }
}