package com.deliveryapp.ui.deliverieslist

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.deliveryapp.MVVMApp
import com.deliveryapp.R
import com.deliveryapp.TestApp
import com.deliveryapp.ui.MainActivity
import com.deliveryapp.util.CustomMatchers
import com.deliveryapp.util.DeliveryDataGenerator.createMockResponse
import com.deliveryapp.util.RecyclerViewMatcher
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class DeliveriesListFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var app: MVVMApp

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        app = instrumentation.targetContext.applicationContext as MVVMApp

        val intent = Intent(instrumentation.targetContext, MainActivity::class.java)
        activityRule.launchActivity(intent)

        val testApp = instrumentation.targetContext.applicationContext as TestApp
        mockWebServer = testApp.getMockServer()

        //instrumentation.targetContext.applicationContext.deleteDatabase(Constants.Db)
    }

    @Test
    fun testMockedWebServer_recycleView() {
        mockWebServer.enqueue(createMockResponse("mock_data_2.json", HttpURLConnection.HTTP_OK))

        //Items would be 3 since a loading item is added on bottom of list
        onView(withId(R.id.rv_deliveries)).check(matches(CustomMatchers.withItemCount(3)))

        onView(listMatcher().atPosition(0)).apply {
            check(matches(hasDescendant(
                    withText(String.format(activityRule.activity.getString(R.string.desc_text),
                            "Deliver documents to Andrio",
                            "Kowloon Tong")))))
        }

        onView(listMatcher().atPosition(1)).apply {
            check(matches(hasDescendant(
                    withText(String.format(activityRule.activity.getString(R.string.desc_text),
                            "Deliver food to Eric",
                            "Mong Kok")))))
        }
    }

    @Test
    fun testError_recycleView() {
        mockWebServer.enqueue(createMockResponse("mock_data_2.json", HttpURLConnection.HTTP_UNAUTHORIZED))
        onView(withId(R.id.rv_deliveries)).check(matches(CustomMatchers.withItemCount(1)))
        onView(Matchers.allOf(withId(R.id.error_msg), withText(app.getString(R.string.inauthenticated_request))))
                .check(matches(isDisplayed()))
    }

    @Test
    fun testItemClick() {
        mockWebServer.enqueue(createMockResponse("mock_data_1.json", HttpURLConnection.HTTP_OK))

        //Items would be 2 since a loading item is added on bottom of list
        onView(withId(R.id.rv_deliveries)).check(matches(CustomMatchers.withItemCount(2)))

        onView(listMatcher().atPosition(0)).perform(ViewActions.click())

        onView(withId(R.id.txt_desc)).check(
                matches(withText(String.format(activityRule.activity.getString(R.string.desc_text),
                        "Deliver documents to Andrio",
                        "Kowloon Tong"))))
    }

    @Test
    fun testSwipeRefresh() {
        mockWebServer.enqueue(createMockResponse("mock_data_2.json", HttpURLConnection.HTTP_OK))
        onView(withId(R.id.swipeContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun testNetworkRunning() {
        mockWebServer.enqueue(createMockResponse("mock_data_2.json", HttpURLConnection.HTTP_OK))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun testInternalServerError() {
        mockWebServer.enqueue(createMockResponse("mock_data_1.json", HttpURLConnection.HTTP_INTERNAL_ERROR))

        onView(listMatcher().atPosition(0)).apply {
            check(matches(isDisplayed()))

            onView(Matchers.allOf(withId(R.id.error_msg), withText(app.getString(R.string.internal_server_error))))
                    .check(matches(isDisplayed()))
        }
    }

    private fun listMatcher() = RecyclerViewMatcher(R.id.rv_deliveries)

    @After
    fun finishActivity() {
        activityRule.finishActivity()
    }
}