package com.deliveryapp.ui.deliverydetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class DeliveryDetailViewModel @Inject constructor() : ViewModel() {
    val results: MutableLiveData<DeliveryData> = MutableLiveData()

    fun setData(data: DeliveryData) {
        results.value = data
    }

}