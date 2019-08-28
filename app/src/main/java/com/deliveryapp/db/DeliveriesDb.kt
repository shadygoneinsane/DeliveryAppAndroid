package com.deliveryapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deliveryapp.models.DeliveryData

@Database(entities = [DeliveryData::class], version = 1)
@TypeConverters(LocationDataTypeConvertor::class)
abstract class DeliveriesDb: RoomDatabase() {
    abstract fun deliveriesDAO(): DeliveriesDAO
}