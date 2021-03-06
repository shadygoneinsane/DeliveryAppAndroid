package com.deliveryapp.di

import com.deliveryapp.BuildConfig
import com.deliveryapp.api.ApiService
import com.deliveryapp.testing.OpenForTesting
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@OpenForTesting
class ApiModule {
    companion object {
        private const val BASE_URL = "baseUrl"
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named(BASE_URL) baseUrl: String,
                        converterFactory: Converter.Factory,
                        httpClient : OkHttpClient.Builder): Retrofit {

        return Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit
                .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun createHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient.Builder{
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            httpClient.addInterceptor(httpLoggingInterceptor)
        return httpClient
    }
}