package com.deliveryapp

import androidx.multidex.MultiDex
import com.deliveryapp.di.DaggerAppComponent
import com.deliveryapp.testing.OpenForTesting
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

@OpenForTesting
class DeliveryApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

}