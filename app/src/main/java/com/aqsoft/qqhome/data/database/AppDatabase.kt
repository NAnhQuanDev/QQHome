package com.aqsoft.qqhome.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aqsoft.qqhome.data.dao.BillDao
import com.aqsoft.qqhome.data.dao.MotelDao
import com.aqsoft.qqhome.data.dao.RoomDao
import com.aqsoft.qqhome.data.dao.ServiceDao
import com.aqsoft.qqhome.data.entity.Bill
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.entity.Service


@Database(entities = [Motel::class, Room::class, Service::class, Bill::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun motelDao(): MotelDao
    abstract fun roomDao(): RoomDao
    abstract fun serviceDao(): ServiceDao
    abstract fun billDao(): BillDao


}