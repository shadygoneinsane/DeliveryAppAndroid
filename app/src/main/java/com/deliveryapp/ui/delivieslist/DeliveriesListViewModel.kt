package com.deliveryapp.ui.delivieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.deliveryapp.api.ApiService
import com.deliveryapp.db.DeliveriesDAO
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.repository.DeliveryListBoundaryCallback
import com.deliveryapp.repository.MainRepository
import com.deliveryapp.repository.NetworkState
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.utils.AppExecutors
import com.deliveryapp.utils.Constants
import com.deliveryapp.utils.Constants.PAGE_SIZE
import com.deliveryapp.utils.Constants.PREFETCH_DISTANCE
import javax.inject.Inject

@OpenForTesting
class DeliveriesListViewModel @Inject constructor(private val mainRepository: MainRepository,
                                                  apiCall: ApiService,
                                                  deliveriesDAO: DeliveriesDAO,
                                                  appExecutors: AppExecutors) : ViewModel() {
    var isNetworkPresent = false
    var refreshState = MutableLiveData<NetworkState>()

    private final var boundaryCallback = DeliveryListBoundaryCallback(
            webservice = apiCall,
            mainRepository = mainRepository,
            networkPageLimit = PAGE_SIZE,
            appExecutors = appExecutors)

    var networkState: MutableLiveData<NetworkState> = boundaryCallback.networkState

    private final val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setPageSize(PAGE_SIZE)
            .build()

    var pagedListData: LiveData<PagedList<DeliveryData>> =
            LivePagedListBuilder(deliveriesDAO.loadAllDeliveriesPagedList(), pagedListConfig)
                    .setBoundaryCallback(boundaryCallback)
                    .build()

    fun refresh() {
        mainRepository.refresh(networkState, refreshState)
    }

    fun retry() {
        if (isNetworkPresent)
            boundaryCallback.retryAllFailed()
        else
            networkState.value = NetworkState.error(Constants.DEFAULT_ERR)
    }
}