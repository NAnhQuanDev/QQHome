package com.aqsoft.qqhome.data.repository

import androidx.lifecycle.LiveData
import com.aqsoft.qqhome.data.dao.RoomDao
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.model.RoomWithBillingStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(private  val RoomDao: RoomDao) {
    suspend fun insertRoom(room: Room) = RoomDao.insertRoom(room)
    suspend fun updateRoom(room: Room) = RoomDao.updateRoom(room)
    suspend fun deleteRoom(room: Room) = RoomDao.deleteRoom(room)
    fun getRoomCountByMotelId(id: Int): LiveData<Int> {
        return RoomDao.getRoomCountByMotelId(id)
    }
    fun getRoomsByMotelId(id: Int): LiveData<List<Room>>{
        return RoomDao.getRoomsByMotelId(id)
    }
    suspend fun getRoomByID(id: Int): Room {
        return RoomDao.getRoomByID(id)
    }
    fun getRoomCountByMotelIdAndRentalStatus(id: Int): LiveData<Int> {
        return RoomDao.getRoomCountByMotelIdAndRentalStatus(id)
    }


    fun getRoomsNotHaveBill(motelID: Int, monthYear: String): LiveData<List<Room>> {
        return RoomDao.getRoomsNotHaveBill(motelID, monthYear)
    }
    fun getRoomCountUnRental(motelID: Int): LiveData<Int> {
        return RoomDao.getRoomCountUnRental(motelID)
    }


}

