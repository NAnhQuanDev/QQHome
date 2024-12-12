package com.aqsoft.qqhome.ui.fragment.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.repository.BillRepository
import com.aqsoft.qqhome.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StatisticsViewModel @Inject constructor(private val RoomRepository: RoomRepository,
                                              private val billRepository: BillRepository) :ViewModel() {
    fun getRoomCountByMotelId(id: Int): LiveData<Int> {
        return try {
            RoomRepository.getRoomCountByMotelId(id)
        } catch (e: Exception) {
            e.printStackTrace()
            MutableLiveData(0)
        }
    }
    fun getRoomCountByMotelIdAndRentalStatus(id: Int): LiveData<Int> {
        return try {
            RoomRepository.getRoomCountByMotelIdAndRentalStatus(id)
        } catch (e: Exception) {
            e.printStackTrace()
            MutableLiveData(0)
        }
    }

    fun getRoomNotHaveBill(motelID: Int, monthYear: String): LiveData<List<Room>> {
        return RoomRepository.getRoomsNotHaveBill(motelID, monthYear)
    }

    fun getPaidBills(monthyear: String, motelID: Int, callback : (Int) -> Unit){
        viewModelScope.launch {
            val paid = billRepository.getPaidBills(monthyear, motelID)
            callback(paid)
        }
    }

    fun getUnpaidBills(monthyear: String, motelID: Int, callback: (Int) -> Unit){
        viewModelScope.launch {
            val unpaid = billRepository.getUnpaidBills(monthyear, motelID)
            callback(unpaid)
        }
    }

}