package com.deliveryapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deliveryapp.ui.deliverydetail.DeliveryDetailViewModel
import com.deliveryapp.ui.delivieslist.DeliveriesListViewModel
import com.deliveryapp.viewmodelfactory.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DeliveriesListViewModel::class)
    abstract fun bindDeliveriesViewModel(deliveriesListViewModel: DeliveriesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryDetailViewModel::class)
    abstract fun bindDeliveryDetailViewModel(deliveriesListViewModel: DeliveryDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
