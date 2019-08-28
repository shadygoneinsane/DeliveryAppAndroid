package com.deliveryapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.util.DeliveryDataGenerator
import com.deliveryapp.util.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OpenForTesting
@RunWith(AndroidJUnit4::class)
class DeliveriesDAOTest : DbTest() {

    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture Components
     * with a different one which executes each task synchronously.
     *
     * Use this rule to instantly execute any background operation on the calling thread
     * */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertionTest() {
        val dataInserted = DeliveryDataGenerator.createDeliveryData(
                0, "Deliver assignment", null,
                "22.12", "114.22", "Earth")
        db.deliveriesDAO().insert(dataInserted)

        val dataRead = LiveDataTestUtil.getValue(db.deliveriesDAO().findById(0))
        assertThat(dataRead.id, `is`(0))
        assertThat(dataRead.description, `is`("Deliver assignment"))
        assertThat(dataRead.location?.lat, `is`(22.12))
        assertThat(dataRead.location?.lng, `is`(114.22))
        assertThat(dataRead.location?.address, `is`("Earth"))
    }

    @Test
    fun insertionMultipleTest() {
        val count = 10
        val dataInserted = DeliveryDataGenerator.createList(count)
        db.deliveriesDAO().insertAll(dataInserted)

        for (i in 0 until count) {
            val dataRead = LiveDataTestUtil.getValue(db.deliveriesDAO().findById(i))
            assertThat(dataRead.id, `is`(i))
            assertThat(dataRead.description, `is`("Deliver assignment"))
            assertThat(dataRead.location?.lat, `is`(22.12))
            assertThat(dataRead.location?.lng, `is`(114.22))
            assertThat(dataRead.location?.address, `is`("Earth"))
        }
    }


    @Test
    fun deletionTest() {
        val dataInserted = DeliveryDataGenerator.createDeliveryDataList(
                10, "Deliver assignment", null,
                "22.12", "114.22", "Earth")
        db.deliveriesDAO().insertAll(dataInserted)

        db.deliveriesDAO().deleteAll()
        val dataRetrieved = db.deliveriesDAO().loadAllDeliveries()
        assertThat(LiveDataTestUtil.getValue(dataRetrieved).size, `is`(0))
    }

}