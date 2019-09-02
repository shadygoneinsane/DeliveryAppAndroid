package com.deliveryapp.repository

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.deliveryapp.api.ApiService
import com.deliveryapp.db.DeliveriesDao
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.models.NetworkState
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.utils.AppExecutors
import com.deliveryapp.utils.Constants.PAGE_SIZE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OpenForTesting
class MainRepository constructor(
        private val apiCall: ApiService,
        private val deliveriesDAO: DeliveriesDao,
        private val appExecutors: AppExecutors) {

    /*
     * run a fresh network request and when it arrives clear the db table and insert all new items.
     */
    @MainThread
    fun refresh(networkState: MutableLiveData<NetworkState>,
                refreshState: MutableLiveData<NetworkState>) {
        networkState.postValue(NetworkState.LOADING)
        /*apiCall.getData(0, PAGE_SIZE).enqueue(
                object : Callback<List<DeliveryData>> {
                    override fun onFailure(call: Call<List<DeliveryData>>, t: Throwable) {
                        networkState.postValue(NetworkState.error(t.message))
                        refreshState.postValue(NetworkState.LOADED)
                    }

                    override fun onResponse(call: Call<List<DeliveryData>>,
                                            response: Response<List<DeliveryData>>) {
                        appExecutors.diskIO().execute {
                            deliveriesDAO.deleteAll()
                            insertResultIntoDb(response.body())
                            networkState.postValue(NetworkState.LOADED)
                            refreshState.postValue(NetworkState.LOADED)
                        }
                    }
                }
        )*/
    }

    fun insertResultIntoDb(list: List<DeliveryData>?) {
        if (null != list && list.isNotEmpty()) deliveriesDAO.insertAll(list)
    }
}