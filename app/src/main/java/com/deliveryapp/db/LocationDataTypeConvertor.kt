package com.deliveryapp.db

import androidx.room.TypeConverter
import com.deliveryapp.models.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationDataTypeConvertor {
    @TypeConverter
    fun fromString(value: String): Location? {
        val listType = object : TypeToken<Location>() {

        }.type
        return Gson().fromJson<Location>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Location): String {
        return Gson().toJson(list)
    }
}