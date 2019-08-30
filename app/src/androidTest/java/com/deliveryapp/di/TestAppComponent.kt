package com.deliveryapp.di

import android.app.Application
import com.deliveryapp.DeliveryApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, MainActivityModule::class, AppModule::class, TestApiModule::class])
interface TestAppComponent : AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

    override fun inject(deliveryApp: DeliveryApp)

    override fun inject(instance: DaggerApplication)

    fun getMockWebServer(): MockWebServer
}