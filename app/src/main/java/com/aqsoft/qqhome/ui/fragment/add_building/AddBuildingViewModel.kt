package com.aqsoft.qqhome.ui.fragment.add_building

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.data.repository.MotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBuildingViewModel @Inject constructor(private val rp: MotelRepository): ViewModel() {

    fun insertMotel(motel: Motel, callback: (Long) -> Unit) = viewModelScope.launch {
        val id = rp.insertMotel(motel)
        callback(id)
    }
}