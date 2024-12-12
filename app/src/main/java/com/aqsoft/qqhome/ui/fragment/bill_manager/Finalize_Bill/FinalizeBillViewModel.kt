package com.aqsoft.qqhome.ui.fragment.bill_manager.Finalize_Bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqsoft.qqhome.data.entity.Bill
import com.aqsoft.qqhome.data.repository.BillRepository
import com.aqsoft.qqhome.data.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinalizeBillViewModel @Inject constructor(private val serviceRepository: ServiceRepository, private val billRepository: BillRepository) : ViewModel() {
    fun getAllService(motelID:Int) = serviceRepository.getAllService(motelID)

    fun insertBill(bill: Bill) {
        viewModelScope.launch {
            billRepository.insertBill(bill)
        }
    }



}