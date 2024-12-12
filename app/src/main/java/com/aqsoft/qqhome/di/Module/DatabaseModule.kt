package com.aqsoft.qqhome.di.Module

import android.app.Application
import androidx.room.RoomDatabase.JournalMode
import com.aqsoft.qqhome.data.dao.BillDao
import com.aqsoft.qqhome.data.dao.MotelDao
import com.aqsoft.qqhome.data.dao.RoomDao
import com.aqsoft.qqhome.data.dao.ServiceDao
import com.aqsoft.qqhome.data.database.AppDatabase
import com.aqsoft.qqhome.ultis.Constants.NameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return androidx.room.Room.databaseBuilder(application,
            AppDatabase::class.java,NameDatabase)
            .setJournalMode(JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMotelDao(appDatabase: AppDatabase) : MotelDao {
        return appDatabase.motelDao()
    }

    @Provides
    fun provideRoomDao(appDatabase: AppDatabase) : RoomDao {
        return appDatabase.roomDao()
    }

    @Provides
    fun provideServiceDao(appDatabase: AppDatabase) : ServiceDao {
        return appDatabase.serviceDao()
    }

    @Provides
    fun provideBillDao(appDatabase: AppDatabase) : BillDao {
        return appDatabase.billDao()
    }



}