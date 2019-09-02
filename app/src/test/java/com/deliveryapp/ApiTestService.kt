package com.deliveryapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deliveryapp.api.ApiService
import com.deliveryapp.di.ApiModule
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.util.DeliveryDataGenerator.createMockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ApiTestService {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: ApiService

    private val mockWebServer = MockWebServer()

    @Before
    fun createService() {
        val apiModule = ApiModule()
        mockWebServer.start()

        val serverUrl = mockWebServer.url("/").toString()
        val gsonConverter = apiModule.provideGsonConverterFactory()
        val createLoggingInterceptor = apiModule.createLoggingInterceptor()
        val httpClient = apiModule.createHttpClient(createLoggingInterceptor)
        val retrofit = apiModule.provideRetrofit(serverUrl, gsonConverter, httpClient)
        apiService = apiModule.provideApiService(retrofit)
    }

    @Test
    fun checkSuccessDeliveryData() {
        mockWebServer.enqueue(createMockResponse("mock_data_4.json", HttpURLConnection.HTTP_OK))
        val response = apiService.getData(0, 20).execute()
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_OK)
        val dataRead = response.body() as List<DeliveryData>
        Assert.assertThat(dataRead.size, CoreMatchers.`is`(4))
        val data = dataRead[0]
        Assert.assertThat(data, IsNull.notNullValue())
        Assert.assertThat(data.imageUrl, CoreMatchers.`is`("https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-1.jpeg"))
        Assert.assertThat(data.description, CoreMatchers.`is`("Deliver documents to Andrio"))
        Assert.assertThat(data.location?.address, CoreMatchers.`is`("Kowloon Tong"))
    }

    @Test
    fun checkFailureDeliveryData() {
        mockWebServer.enqueue(createMockResponse("mock_data_4.json", HttpURLConnection.HTTP_BAD_GATEWAY))
        val response = apiService.getData(0, 20).execute()
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_BAD_GATEWAY)
        Assert.assertThat(response.message(), CoreMatchers.`is`("Server Error"))
        Assert.assertThat(response.body(), IsNull.nullValue())
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }
}