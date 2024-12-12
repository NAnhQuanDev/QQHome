package com.aqsoft.qqhome.ui.fragment.room_manager.dialogs


import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.core.text.isDigitsOnly
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.databinding.BottomsheetAddRoomBinding
import com.aqsoft.qqhome.ultis.UiHelper.formatEditTextAsVND
import com.aqsoft.qqhome.ultis.UiHelper.hideKeyboard
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddRoomBottomSheet(
    context: Context,
    private val motelId: Int,
    private val onRoomAdded: (Room) -> Unit,
    private val onShowToast: (String) -> Unit
) : BottomSheetDialog(context, R.style.TransparentBottomSheetStyle) {

    private val binding = BottomsheetAddRoomBinding.inflate(LayoutInflater.from(context))

    init {
        initializeDialog()
        setupViews()
    }

    private fun initializeDialog() {
        setContentView(binding.root)
        window?.setWindowAnimations(0)
        setCancelable(true)
        behavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
            isDraggable = true
        }
    }

    private fun setupViews() {
        with(binding) {

            setupScrollViewTouch()
            btnSaveRoom.setOnClickListener { validateAndSaveRoom() }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    private fun setupScrollViewTouch() {
        binding.scrollView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideKeyboard(view, context)
            }
            false
        }
    }

    private fun validateAndSaveRoom() {
        with(binding) {
            val roomName = edtRoomName.text.toString().trim()
            val floorNumber = edtFloorName.text.toString().trim()


            when {
                roomName.isBlank() -> showValidationError("Vui lòng nhập tên phòng!")
                !floorNumber.isDigitsOnly() || floorNumber.isBlank() -> showValidationError("Số tầng phải là số hợp lệ hoặc không được để trống!")
                else -> createAndSaveRoom(roomName, floorNumber.toInt())
            }
        }
    }

    private fun showValidationError(message: String) {
        onShowToast(message)
    }

    private fun createAndSaveRoom(name: String, floor: Int) {
        val newRoom = Room(
            RoomName = name,
            Floor = floor,
            Roomprice = 0,
            Rentalstatus = false,
            TenantName = "null",
            MotelID = motelId,
            TenantPhone = "",
            Rentalstartdate = ""
        )
        onRoomAdded(newRoom)
        onShowToast("Thêm phòng thành công!")
        dismiss()
    }

    companion object {
        fun show(
            context: Context,
            motelId: Int,
            onRoomAdded: (Room) -> Unit,
            onShowToast: (String) -> Unit
        ) = AddRoomBottomSheet(context, motelId, onRoomAdded, onShowToast).show()
    }
}