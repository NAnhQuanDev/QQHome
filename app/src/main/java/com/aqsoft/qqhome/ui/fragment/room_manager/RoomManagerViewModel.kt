package com.aqsoft.qqhome.ui.fragment.room_manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.model.RoomWithBillingStatus
import com.aqsoft.qqhome.data.repository.BillRepository
import com.aqsoft.qqhome.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel


class RoomManagerViewModel @Inject constructor(private val rp: RoomRepository) : ViewModel() {
    fun insertRoom(room: Room) = viewModelScope.launch { rp.insertRoom(room) }

    fun getRoomsByMotelId(id: Int) : LiveData<List<Room>>{
        return rp.getRoomsByMotelId(id)
    }

    fun updateRoom(room: Room) = viewModelScope.launch { rp.updateRoom(room) }


}