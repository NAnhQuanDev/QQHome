package com.aqsoft.qqhome.ui.fragment.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Service
import com.aqsoft.qqhome.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(private val rp: ServiceRepository) : ViewModel() {
    fun insertService(service: Service) {
        viewModelScope.launch {
            rp.insertService(service)
        }
    }
    fun getAllService(motelID:Int): LiveData<List<Service>> {
        return rp.getAllService(motelID)
    }
    fun deleteService(service: Service) {
        viewModelScope.launch {
            rp.deleteService(service)
        }
    }
}