package com.aqsoft.qqhome.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aqsoft.qqhome.data.entity.Motel

@Dao

interface MotelDao{
    @Insert
    suspend fun insertMotel(motel: Motel):Long
    @Update
    suspend fun updateMotel(motel: Motel)

    @Query("DELETE FROM Motel WHERE MotelID = :id")
    suspend fun deleteMotelByID(id: Int)

    @Query("SELECT * FROM Motel WHERE MotelID = :id")
    suspend fun getMotelByID(id: Int): Motel

    @Query("SELECT * FROM Motel")
    suspend fun getAllMotel(): List<Motel>





}