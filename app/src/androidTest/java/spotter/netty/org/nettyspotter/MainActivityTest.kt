package spotter.netty.org.nettyspotter

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView

import org.hamcrest.CoreMatchers

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

import spotter.netty.org.nettyspotter.ui.MainActivity
import spotter.netty.org.nettyspotter.ui.MapsActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Intents.init()
    }

    @Test
    fun openMapsActivityTest() {
        onView(ViewMatchers.withId(R.id.openMap)).perform(ViewActions.click())
        intended(hasComponent(MapsActivity::class.java.name))
    }

    @Test
    @Throws(Exception::class)
    fun ensureListTest() {
        val activity = activityRule.activity
        val viewById = activity.findViewById<RecyclerView>(R.id.list)
        ViewMatchers.assertThat(viewById, CoreMatchers.notNullValue())
        ViewMatchers.assertThat(viewById, CoreMatchers.instanceOf(RecyclerView::class.java))
    }
}
