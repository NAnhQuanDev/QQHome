package com.aqsoft.qqhome.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.data.repository.MotelRepository
import com.aqsoft.qqhome.data.repository.RoomRepository
import com.aqsoft.qqhome.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rpmotel: MotelRepository,
    private val rproom: RoomRepository,
    private val rpservice: ServiceRepository
) : ViewModel() {

    fun getMotelByID(id: Int, callback: (Motel?) -> Unit) {
        viewModelScope.launch {
            try {
                val motel = withContext(Dispatchers.IO) { rpmotel.getMotelByID(id) }
                callback(motel)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(null)
            }
        }
    }

    fun getRoomCountByMotelId(id: Int): LiveData<Int> {
        return try {
            rproom.getRoomCountByMotelId(id)
        } catch (e: Exception) {
            e.printStackTrace()
            MutableLiveData(0)
        }
    }

    fun getAllMotel(callback: (List<Motel>) -> Unit) {
        viewModelScope.launch {
            try {
                val motels = withContext(Dispatchers.IO) { rpmotel.getAllMotel() }
                callback(motels)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(emptyList())
            }
        }
    }

    fun deleteMotelById(id: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    rpmotel.deleteMotelById(id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getRoomCountByMotelIdAndRentalStatus(id: Int): LiveData<Int> {
        return try {
            rproom.getRoomCountByMotelIdAndRentalStatus(id)
        } catch (e: Exception) {
            e.printStackTrace()
            MutableLiveData(0)
        }
    }

    fun getServiceCount(motelID:Int) = rpservice.getServiceCount(motelID)

    fun getRoomCountUnRental(motelID: Int) = rproom.getRoomCountUnRental(motelID)
}
