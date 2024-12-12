package com.aqsoft.qqhome.data.repository

import com.aqsoft.qqhome.data.dao.MotelDao
import com.aqsoft.qqhome.data.entity.Motel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MotelRepository @Inject constructor(private val motelDao: MotelDao){

    suspend fun insertMotel(motel: Motel): Long = motelDao.insertMotel(motel)
    suspend fun deleteMotelById(id: Int) = motelDao.deleteMotelByID(id)
    suspend fun getMotelByID(id:Int): Motel = motelDao.getMotelByID(id)
    suspend fun getAllMotel(): List<Motel> = motelDao.getAllMotel()

}
