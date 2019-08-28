package com.deliveryapp.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(

        @SerializedName("lat")
        @Expose
        var lat: Double? = null,

        @SerializedName("lng")
        @Expose
        var lng: Double? = null,

        @SerializedName("address")
        @Expose
        var address: String? = null
) : Parcelable