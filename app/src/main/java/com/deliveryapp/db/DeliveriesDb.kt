package com.deliveryapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deliveryapp.models.DeliveryData

@Database(entities = [DeliveryData::class], version = 1)
abstract class DeliveriesDb: RoomDatabase() {
    abstract fun deliveriesDAO(): DeliveriesDao
}