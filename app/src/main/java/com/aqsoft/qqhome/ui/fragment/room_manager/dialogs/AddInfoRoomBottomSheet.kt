package com.aqsoft.qqhome.ui.fragment.room_manager.dialogs


import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.databinding.BottomshetAddInforoomBinding
import com.aqsoft.qqhome.ultis.UiHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddInfoRoomBottomSheet(
    context: Context,
    private val room: Room,
    private val onRoomUpdated: (Room) -> Unit
) : BottomSheetDialog(context, R.style.TransparentBottomSheetStyle) {

    private val binding = BottomshetAddInforoomBinding.inflate(LayoutInflater.from(context))
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        setContentView(binding.root)
        setupDialog()
        setupViews()
    }

    private fun setupDialog() {
        behavior.apply {
            skipCollapsed = true
            isDraggable = true
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        window?.setWindowAnimations(0)
        setCancelable(true)

    }

    private fun setupViews() {
        UiHelper.formatEditTextAsVND(binding.edtRoomPrice)
        binding.btnSaveRoom.setOnClickListener {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    val updatedRoom = updateRoomInfo()
                    withContext(Dispatchers.Main) {
                        onRoomUpdated(updatedRoom)
                        dismiss()
                    }
                }
            }
        }
        binding.btnCancel.setOnClickListener{
            dismiss()
        }
    }

    private fun updateRoomInfo() = room.copy(
        RoomName = room.RoomName,
        MotelID = room.MotelID,
        Floor = room.Floor,
        TenantName = binding.edtTenantName.text.toString(),
        TenantPhone = binding.TenantPhone.text.toString(),
        Rentalstatus = true,
        Roomprice = binding.edtRoomPrice.text.toString().replace("[^\\d]".toRegex(), "").toLong(),
        Rentalstartdate = getCurrentDate()


    )

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun dismiss() {
        binding.root.removeAllViews()
        super.dismiss()
    }

    companion object {
        fun show(
            context: Context,
            room: Room,
            onRoomUpdated: (Room) -> Unit
        ) = AddInfoRoomBottomSheet(context, room, onRoomUpdated).show()
    }
}