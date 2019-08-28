package com.deliveryapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deliveryapp.models.DeliveryData
import androidx.paging.DataSource

@Dao
interface DeliveriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DeliveryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<DeliveryData>)

    @Query("SELECT * FROM deliverydata WHERE id = :id")
    fun findById(id: Int): LiveData<DeliveryData>

    @Query("SELECT * FROM deliverydata")
    fun loadAllDeliveries(): LiveData<List<DeliveryData>>

    @Query("SELECT * FROM deliverydata")
    fun loadAllDeliveriesPagedList(): DataSource.Factory<Int, DeliveryData>

    @Query("SELECT MAX(id) + 1 FROM deliverydata")
    fun getNextIndexInList() : Int

    @Query("DELETE FROM deliverydata")
    fun deleteAll()
}