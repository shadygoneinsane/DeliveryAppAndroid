package com.deliveryapp.api

import com.deliveryapp.models.DeliveryData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("deliveries")
    suspend fun getData(@Query("offset") offset: Int,
                @Query("limit") limit: Int
    ): Response<List<DeliveryData>>
}