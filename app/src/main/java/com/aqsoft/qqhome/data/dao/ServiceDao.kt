package com.aqsoft.qqhome.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aqsoft.qqhome.data.entity.Service

@Dao
interface ServiceDao {

    @Insert
    suspend fun insertService(service: Service)
    @Update
    suspend fun updateService(service: Service)

    @Delete
    suspend fun deleteService(service: Service)

    @Query("SELECT * FROM Service WHERE MotelID = :motelID")
    fun getAllService(motelID:Int): LiveData<List<Service>>

    @Query("SELECT COUNT(*) FROM Service WHERE MotelID = :motelID")
    fun getServiceCount(motelID:Int): LiveData<Int>
}