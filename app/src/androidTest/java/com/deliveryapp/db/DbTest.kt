package com.deliveryapp.db


import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

abstract class DbTest {
    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture Components
     * with a different one which executes each task synchronously.
     *
     * Use this rule to wait on background operations of Architecture Components
     * */
    @get:Rule
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    private lateinit var deliveriesDb: DeliveriesDb

    val db: DeliveriesDb
        get() = deliveriesDb

    @Before
    fun initDb() {
        deliveriesDb = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DeliveriesDb::class.java).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        deliveriesDb.close()
    }
}