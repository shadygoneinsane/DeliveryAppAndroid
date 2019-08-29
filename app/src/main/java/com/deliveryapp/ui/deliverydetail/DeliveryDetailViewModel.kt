package com.deliveryapp.ui.deliverydetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deliveryapp.db.DeliveriesDao
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.utils.AppExecutors
import javax.inject.Inject

@OpenForTesting
class DeliveryDetailViewModel @Inject constructor(private val deliveriesDAO: DeliveriesDao,
                                                  private val appExecutors: AppExecutors) : ViewModel() {

    var results: MutableLiveData<DeliveryData> = MutableLiveData()

    fun setData(dataId: Int) {
        appExecutors.diskIO().execute {
            results.postValue(deliveriesDAO.findById(dataId))
        }
    }

}