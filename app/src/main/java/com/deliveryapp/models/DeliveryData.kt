package com.deliveryapp.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id"])
class DeliveryData(

        @field:SerializedName("id")
        @Expose
        val id: Int,

        @field:SerializedName("description")
        @Expose
        var description: String? = null,

        @field:SerializedName("imageUrl")
        @Expose
        var imageUrl: String? = null,

        @field:SerializedName("location")
        @Expose
        @Embedded
        var location: Location? = null
) : Parcelable
