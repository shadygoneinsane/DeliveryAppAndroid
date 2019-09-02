package com.deliveryapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.deliveryapp.api.ApiService
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.models.NetworkState
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.utils.AppExecutors
import com.deliveryapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

@OpenForTesting
class DeliveryListBoundaryCallback(private val webservice: ApiService,
                                   private val mainRepository: MainRepository,
                                   private val appExecutors: AppExecutors) : PagedList.BoundaryCallback<DeliveryData>() {
    private var requestQueue: Call<List<DeliveryData>>? = null
    val networkState = MutableLiveData<NetworkState>()

    override fun onZeroItemsLoaded() {
        networkState.postValue(NetworkState.LOADING)
        webservice.getData(0, Constants.PAGE_SIZE).enqueue(WebserviceCallback())
    }

    override fun onItemAtEndLoaded(itemAtEnd: DeliveryData) {
        networkState.postValue(NetworkState.LOADING)
        webservice.getData((itemAtEnd.id + 1), Constants.PAGE_SIZE).enqueue(WebserviceCallback())
    }

    fun retryAllFailed() {
        requestQueue?.let {
            networkState.postValue(NetworkState.LOADING)
            it.enqueue(WebserviceCallback())
        }
    }

    fun queueRequest(call: Call<List<DeliveryData>>) {
        requestQueue = call.clone()
    }

    private fun handleResponse(response: Response<List<DeliveryData>>) {
        appExecutors.diskIO().execute {
            mainRepository.insertResultIntoDb(response.body())
            networkState.postValue(NetworkState.LOADED)
        }
    }

    inner class WebserviceCallback : Callback<List<DeliveryData>> {
        override fun onFailure(call: Call<List<DeliveryData>>, t: Throwable) {
            networkState.postValue(NetworkState.error(Constants.DEFAULT_ERR))
            queueRequest(call.clone())
        }

        override fun onResponse(call: Call<List<DeliveryData>>, response: Response<List<DeliveryData>>) {
            if (response.code() == HttpURLConnection.HTTP_OK)
                handleResponse(response)
            else {
                networkState.postValue(NetworkState.error(response.code()))
                queueRequest(call.clone())
            }
        }
    }
}