package com.aqsoft.qqhome.ui.fragment.bill_manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Bill
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.repository.BillRepository
import com.aqsoft.qqhome.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BillViewModel @Inject constructor(private val rp: RoomRepository,private val bill:BillRepository) : ViewModel() {
    fun getRoomNotHaveBill(motelID: Int, monthYear: String): LiveData<List<Room>> {
        return rp.getRoomsNotHaveBill(motelID, monthYear)
    }
    fun getBillByMonthYear(motelID: Int, monthYear: String) = bill.getBillByMonthYear(motelID, monthYear)

    fun deleteBill(billID: Bill) {
        viewModelScope.launch {
            bill.deleteBill(billID)
        }
    }
    fun updateBill(billId: Int, status: Boolean) {
        viewModelScope.launch {
            bill.updateBill(billId, status)
        }
    }


}