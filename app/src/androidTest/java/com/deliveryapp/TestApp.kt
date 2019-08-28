package com.deliveryapp

import androidx.test.platform.app.InstrumentationRegistry
import com.deliveryapp.di.DaggerTestAppComponent
import okhttp3.mockwebserver.MockWebServer

class TestApp : MVVMApp() {

    lateinit var mockWebServer: MockWebServer

    override fun onCreate() {
        super.onCreate()

        val instrumentation = InstrumentationRegistry.getInstrumentation()

        val app = instrumentation.targetContext.applicationContext as MVVMApp

        val appInjector = DaggerTestAppComponent
                .builder()
                .application(app)
                .build()

        appInjector.inject(app)

        mockWebServer = appInjector.getMockWebServer()
    }

    fun getMockServer(): MockWebServer {
        return mockWebServer
    }
}