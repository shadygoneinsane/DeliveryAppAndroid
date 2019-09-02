package com.deliveryapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import com.deliveryapp.api.ApiService
import com.deliveryapp.db.DeliveriesDao
import com.deliveryapp.db.DeliveriesDb
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.repository.DeliveryListBoundaryCallback
import com.deliveryapp.repository.MainRepository
import com.deliveryapp.ui.delivieslist.DeliveriesListViewModel
import com.deliveryapp.util.DeliveryDataGenerator
import com.deliveryapp.util.InstantAppExecutors
import com.deliveryapp.util.mock
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class DeliveriesViewModelUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val deliveriesDb = Mockito.mock(DeliveriesDb::class.java)
    private val apiService = Mockito.mock(ApiService::class.java)
    private val deliveriesDAO = Mockito.mock(DeliveriesDao::class.java)
    private val boundaryCallBack = Mockito.mock(DeliveryListBoundaryCallback::class.java)

    private lateinit var mainRepository: MainRepository
    private lateinit var listViewModel: DeliveriesListViewModel

    @Before
    fun doBefore() {
        Mockito.`when`(deliveriesDb.deliveriesDAO()).thenReturn(deliveriesDAO)
        val factory: DataSource.Factory<Int, DeliveryData> = mock()
        Mockito.`when`(deliveriesDAO.loadAllDeliveriesPagedList()).thenReturn(factory)
        mainRepository = MainRepository(
                appExecutors = InstantAppExecutors(),
                deliveriesDAO = deliveriesDAO,
                apiCall = apiService)

        listViewModel = DeliveriesListViewModel(mainRepository, deliveriesDAO, boundaryCallBack)
    }

    @Test
    fun testNotNull() {
        assertThat(deliveriesDAO, notNullValue())
        assertThat(apiService, notNullValue())
        assertThat(mainRepository, notNullValue())
        assertThat(boundaryCallBack, notNullValue())
    }

    @Test
    fun testBasicData() {
        val dbDeliveriesData = MutableLiveData<List<DeliveryData>>()
        val observer = mock<Observer<List<DeliveryData>>>()
        Mockito.`when`(deliveriesDAO.loadAllDeliveries()).thenReturn(dbDeliveriesData)
        val list = DeliveryDataGenerator.createList(4)
        dbDeliveriesData.observeForever(observer)
        dbDeliveriesData.value = list
        verify(observer).onChanged(list)
    }

    /*@Test
    fun testData() {
        val dbDeliveriesData = MutableLiveData<PagedList<DeliveryData>>()
        val observer = mock<Observer<PagedList<DeliveryData>>>()

        listViewModel.pagedListData.observeForever(observer)

        val pagedList = mockPagedList(DeliveryDataGenerator.createList(4))

        dbDeliveriesData.value = pagedList

        verify(observer).onChanged(pagedList)
    }

    private fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList[ArgumentMatchers.anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }*/
}