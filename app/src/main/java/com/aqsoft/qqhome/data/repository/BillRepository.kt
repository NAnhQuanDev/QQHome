package com.aqsoft.qqhome.data.repository

import androidx.lifecycle.LiveData
import com.aqsoft.qqhome.data.dao.BillDao
import com.aqsoft.qqhome.data.entity.Bill
import javax.inject.Inject

class BillRepository @Inject constructor(private val billDao: BillDao) {
    suspend fun insertBill(bill: Bill) {
        billDao.insertBill(bill)
    }
    fun getBillByMonthYear(motelID: Int, monthYear: String): LiveData<List<Bill>> {
        return billDao.getBillByMonthYear(motelID, monthYear)
    }
    suspend fun deleteBill(bill: Bill) {
        billDao.deleteBill(bill)
    }
    suspend fun updateBill(billId: Int, status: Boolean) {
        billDao.updateBill(billId, status)
    }

    suspend fun getPaidBills(monthyear: String, motelID: Int): Int {
        return billDao.getPaidBills(monthyear, motelID)
    }
    suspend fun getUnpaidBills(monthyear: String, motelID: Int): Int {
        return billDao.getUnpaidBills(monthyear, motelID)
    }
}