package com.aqsoft.qqhome.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.model.RoomWithBillingStatus

@Dao

interface RoomDao {
    @Insert
    suspend fun insertRoom(room: Room)

    @Update
    suspend fun updateRoom(room: Room)

    @Delete
    suspend fun deleteRoom(room: Room)


    @Query("SELECT * FROM ROOM WHERE MotelID = :ID")
    fun getRoomsByMotelId(ID: Int): LiveData<List<Room>>

    @Query("SELECT COUNT(*) FROM ROOM WHERE MotelID = :ID")
    fun getRoomCountByMotelId(ID: Int): LiveData<Int>

    @Query("SELECT * FROM Room WHERE RoomID = :ID")
    suspend fun getRoomByID(ID: Int): Room

    @Query("SELECT COUNT(*) FROM Room WHERE MotelID = :ID AND Rentalstatus = true")
    fun getRoomCountByMotelIdAndRentalStatus(ID: Int): LiveData<Int>

    @Query("SELECT COUNT(*) FROM Room WHERE MotelID = :motelID AND Rentalstatus = false")
    fun getRoomCountUnRental(motelID: Int): LiveData<Int>


    @Query(
        """
    SELECT r.RoomID, r.RoomName, r.MotelID, r.Floor, r.Roomprice, r.Rentalstatus, r.TenantName, r.TenantPhone, r.Rentalstartdate
    FROM Room r
    LEFT JOIN Bill b ON r.RoomID = b.RoomID AND b.Monthyear = :monthYear
    WHERE r.MotelID = :motelID
    AND b.BillID IS NULL
    AND r.Rentalstatus = 1
"""
    )
    fun getRoomsNotHaveBill(motelID: Int, monthYear: String): LiveData<List<Room>>


}

