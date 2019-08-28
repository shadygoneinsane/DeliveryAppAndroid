package com.deliveryapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.deliveryapp.api.ApiService
import com.deliveryapp.db.DeliveriesDAO
import com.deliveryapp.db.DeliveriesDb
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.repository.MainRepository
import com.deliveryapp.util.DeliveryDataGenerator
import com.deliveryapp.util.InstantAppExecutors
import com.deliveryapp.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class MainRepositoryTest {

    private val deliveriesDAO = Mockito.mock(DeliveriesDAO::class.java)
    private val apiService = Mockito.mock(ApiService::class.java)
    private val deliveriesDb = Mockito.mock(DeliveriesDb::class.java)
    private lateinit var mainRepository: MainRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        `when`(deliveriesDb.deliveriesDAO()).thenReturn(deliveriesDAO)
        mainRepository = MainRepository(
                appExecutors = InstantAppExecutors(),
                deliveriesDAO = deliveriesDAO,
                apiCall = apiService)
    }


    @Test
    fun testLoadData() {
        val dbDeliveriesData = MutableLiveData<List<DeliveryData>>()
        val observer = mock<Observer<List<DeliveryData>>>()
        `when`(deliveriesDAO.loadAllDeliveries()).thenReturn(dbDeliveriesData)
        val list = DeliveryDataGenerator.createList(4)
        dbDeliveriesData.observeForever(observer)
        dbDeliveriesData.value = list
        verify(observer).onChanged(list)
    }
}