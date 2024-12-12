package com.aqsoft.qqhome.ui.fragment.room_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.databinding.FragmentDetailRoomBinding
import com.aqsoft.qqhome.ultis.UiHelper.formatTextAsVND
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.leinardi.android.speeddial.SpeedDialActionItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRoomFragment : BaseFragment<FragmentDetailRoomBinding>() {
    private val args: DetailRoomFragmentArgs by navArgs()
    private lateinit var room: Room

    private val detailroomViewmodel: DetailRoom_ViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDetailRoomBinding {
        return FragmentDetailRoomBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        getDataRoom()

    }

    private fun getDataRoom() {
        room = args.room
        binding.roomname.text = "Tên phòng: ${room.RoomName}"
        binding.floor.text = "Tầng: ${room.Floor}"
        binding.priceroom.text = "Giá phòng: ${formatTextAsVND(room.Roomprice)} VND/ tháng"
        binding.tenantname.text = "Tên người thuê: ${room.TenantName}"
        binding.tenantphone.text = "Số điện thoại: ${room.TenantPhone}"
        binding.starttime.text = "Ngày bắt đầu thuê: ${room.Rentalstartdate}"
    }

    private fun setListener() {
        binding.backIcon.setOnClickListener { findNavController().popBackStack() }
        bindingFloatActionEditRoom()

    }

    private fun bindingFloatActionEditRoom() {
        binding.editroom.mainFabAnimationRotateAngle = 0f
        binding.editroom.apply {
            addActionItem(
                SpeedDialActionItem.Builder(R.id.aaa, R.drawable.baseline_delete_24)
                    .setLabel("Xóa phòng").setFabBackgroundColor(resources.getColor(R.color.red))
                    .setFabImageTintColor(resources.getColor(R.color.white)).create()
            )
            addActionItem(
                SpeedDialActionItem.Builder(R.id.editroom, R.drawable.baseline_edit_24)
                    .setLabel("Sửa thông tin phòng")
                    .setFabBackgroundColor(resources.getColor(R.color.yellow))
                    .setFabImageTintColor(resources.getColor(R.color.white)).create()

            )
            setOnActionSelectedListener { actionItems ->
                when (actionItems.id) {
                    R.id.editroom -> {
                        val action = DetailRoomFragmentDirections.actionDetailRoomFragmentToUpdateInfoRoom(room)
                        findNavController().navigate(action)
                    }

                    R.id.aaa -> {
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Xác nhận")
                            .setMessage("Bạn có chắc thực hiện thao xác xóa này, dữ liệu sẽ không thể khôi phục?")
                            .setPositiveButton("Đồng ý") { dialog, _ ->
                                dialog.dismiss()
                                detailroomViewmodel.deleteRoom(room)
                                findNavController().popBackStack()
                            }
                            .setNegativeButton("Hủy") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
                false
            }

        }

    }
}