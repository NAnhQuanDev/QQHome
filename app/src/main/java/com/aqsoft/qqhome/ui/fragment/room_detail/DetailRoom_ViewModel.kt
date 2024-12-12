package com.aqsoft.qqhome.ui.fragment.room_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRoom_ViewModel @Inject constructor(private val rp: RoomRepository) : ViewModel() {


    fun getRoomByID(id: Int, callback: (Room) -> Unit) = viewModelScope.launch {
        val room = rp.getRoomByID(id)
        callback(room)
    }
    fun deleteRoom(room: Room) = viewModelScope.launch {
        rp.deleteRoom(room)
    }
    fun updateRoom(room: Room) = viewModelScope.launch {
        rp.updateRoom(room)
    }
}
