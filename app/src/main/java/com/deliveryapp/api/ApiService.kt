package com.deliveryapp.api

import com.deliveryapp.models.DeliveryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("deliveries")
    fun getData(@Query("offset") offset: Int,
                @Query("limit") limit: Int
    ): Call<List<DeliveryData>>
}