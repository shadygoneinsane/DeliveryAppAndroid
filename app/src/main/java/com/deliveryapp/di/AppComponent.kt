package com.deliveryapp.di

import android.app.Application
import com.deliveryapp.DeliveryApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, MainActivityModule::class, UrlModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(deliveryApp: DeliveryApp)

    override fun inject(instance: DaggerApplication)
}