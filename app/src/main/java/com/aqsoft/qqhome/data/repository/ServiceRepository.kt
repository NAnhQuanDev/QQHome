package com.aqsoft.qqhome.data.repository

import com.aqsoft.qqhome.data.dao.ServiceDao
import com.aqsoft.qqhome.data.entity.Service
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(private val serviceDao: ServiceDao) {

    suspend fun insertService(service: Service) = serviceDao.insertService(service)
    suspend fun deleteService(service: Service) = serviceDao.deleteService(service)
    fun getAllService(motelID:Int) = serviceDao.getAllService(motelID)
    fun getServiceCount(motelID:Int) = serviceDao.getServiceCount(motelID)
}
