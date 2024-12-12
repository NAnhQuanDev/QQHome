package com.aqsoft.qqhome.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aqsoft.qqhome.data.entity.Bill

@Dao
interface BillDao {
    @Insert
    suspend fun insertBill(bill: Bill)


    @Query("SELECT * FROM Bill WHERE Monthyear = :monthYear AND MotelID = :motelID")
    fun getBillByMonthYear(motelID :Int,monthYear: String): LiveData<List<Bill>>

    @Delete
    suspend fun deleteBill(bill: Bill)

    @Query("UPDATE Bill SET Paymentstatus = :status WHERE BillID = :billId")
    suspend fun updateBill(billId: Int, status: Boolean)

    @Query("SELECT COUNT(*) FROM Bill WHERE Paymentstatus = 1 AND Monthyear = :monthyear AND MotelID = :motelID")
    suspend fun getPaidBills(monthyear: String,motelID: Int): Int


    @Query("SELECT COUNT(*) FROM Bill WHERE Paymentstatus = 0 AND Monthyear = :monthyear AND MotelID = :motelID")
    suspend fun getUnpaidBills(monthyear: String,motelID: Int): Int






}