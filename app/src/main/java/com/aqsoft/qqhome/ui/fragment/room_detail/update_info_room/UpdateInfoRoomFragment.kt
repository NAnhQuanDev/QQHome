package com.aqsoft.qqhome.ui.fragment.room_detail.update_info_room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.databinding.FragmentUpdateInfoRoomBinding
import com.aqsoft.qqhome.ui.fragment.room_detail.DetailRoom_ViewModel
import com.aqsoft.qqhome.ultis.UiHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateInfoRoomFragment : BaseFragment<FragmentUpdateInfoRoomBinding>() {
    private val args: UpdateInfoRoomFragmentArgs by navArgs()
    private val DetailRoomFragment: DetailRoom_ViewModel by viewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateInfoRoomBinding {
        return FragmentUpdateInfoRoomBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewwithData()
        setListener()
    }

    private fun setListener() {
        UiHelper.formatEditTextAsVND(binding.priceroom)
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnUpdate.setOnClickListener {
            val roomName = binding.roomname.text.toString().trim()
            val floor = binding.floor.text.toString().trim()
            val priceRoom = binding.priceroom.text.toString().replace("[^\\d]".toRegex(), "")
            val tenantName = binding.tenantname.text.toString().trim()
            val tenantPhone = binding.tenantphone.text.toString().trim()

            if (roomName.isEmpty() || floor.isEmpty() || priceRoom.isEmpty() || tenantName.isEmpty()) {
                showToast("Vui lòng điền đầy đủ thông tin")
                return@setOnClickListener
            }

            val oldRoom = args.room
            if (roomName == oldRoom.RoomName &&
                floor.toInt() == oldRoom.Floor &&
                priceRoom.toLong() == oldRoom.Roomprice &&
                tenantName == oldRoom.TenantName &&
                tenantPhone == oldRoom.TenantPhone
            ) {
                showToast("Không có thông tin nào thay đổi")
                return@setOnClickListener
            }


            val updatedRoom = oldRoom.copy(
                RoomName = roomName,
                Floor = floor.toInt(),
                Roomprice = priceRoom.toLong(),
                TenantName = tenantName,
                TenantPhone = tenantPhone,
                Rentalstartdate = oldRoom.Rentalstartdate,
                MotelID = oldRoom.MotelID,
                Rentalstatus = oldRoom.Rentalstatus
            )


            DetailRoomFragment.updateRoom(updatedRoom)

            val action = UpdateInfoRoomFragmentDirections
                .actionUpdateInfoRoomToDetailRoomFragment(updatedRoom)
            findNavController().navigate(action)
        }
    }

    private fun setViewwithData() {
        binding.roomname.setText(args.room.RoomName)
        binding.floor.setText(args.room.Floor.toString())
        binding.priceroom.setText(UiHelper.formatTextAsVND(args.room.Roomprice))
        binding.tenantname.setText(args.room.TenantName)
        binding.tenantphone.setText(args.room.TenantPhone)
    }


}