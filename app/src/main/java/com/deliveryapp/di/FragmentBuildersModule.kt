package com.deliveryapp.di

import com.deliveryapp.ui.deliverydetail.DeliveryDetailFragment
import com.deliveryapp.ui.delivieslist.DeliveriesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeDeliveriesFragment(): DeliveriesListFragment

    @ContributesAndroidInjector
    abstract fun contributeDeliveryDetailFragment(): DeliveryDetailFragment

}
