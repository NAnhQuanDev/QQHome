package com.aqsoft.qqhome.ui.fragment.room_manager

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.model.FloorModel
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.databinding.FragmentRoomManagerBinding
import com.aqsoft.qqhome.ui.fragment.room_manager.adapters.FloorManagerAdapter
import com.aqsoft.qqhome.ui.fragment.room_manager.adapters.IClickRoomAdapter
import com.aqsoft.qqhome.ui.fragment.room_manager.dialogs.AddInfoRoomBottomSheet
import com.aqsoft.qqhome.ui.fragment.room_manager.dialogs.AddRoomBottomSheet
import com.aqsoft.qqhome.ultis.Constants.DEFAULT_MOTEL
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomManagerFragment : BaseFragment<FragmentRoomManagerBinding>(), IClickRoomAdapter {
    private val viewModel: RoomManagerViewModel by viewModels()
    private var roomAdapter: FloorManagerAdapter? = null
    private var originalRooms: List<Room> = emptyList()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRoomManagerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        with(binding) {
            backIcon.setOnClickListener { findNavController().popBackStack() }
            fabAdd.setOnClickListener { showAddRoomDialog() }
            rv.layoutManager = LinearLayoutManager(requireContext())

            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    filterRooms(s.toString())
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun observeData() {
        val motelId = SharedPreferencesHelper.getString(requireContext(), DEFAULT_MOTEL, "")
            ?.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: return

        viewModel.getRoomsByMotelId(motelId).observe(viewLifecycleOwner) { rooms ->
            binding.nothing.visibility = if (rooms.isNullOrEmpty()) View.VISIBLE else View.GONE
            rooms?.let {
                originalRooms = it
                updateRoomList(it)
            }
        }
    }

    private fun updateRoomList(rooms: List<Room>) {
        roomAdapter = FloorManagerAdapter(subListRoom(rooms), this).also { adapter ->
            binding.rv.adapter = adapter
        }
    }

    private fun filterRooms(query: String) {
        val filteredRooms = if (query.isEmpty()) {
            originalRooms
        } else {
            originalRooms.filter { room ->
                room.RoomName.contains(query, ignoreCase = true)
            }
        }
        updateRoomList(filteredRooms)
    }

    private fun showAddRoomDialog() {
        val motelId = SharedPreferencesHelper.getString(requireContext(), DEFAULT_MOTEL, "")
            ?.toIntOrNull() ?: run {
            showToast("Vui lòng thêm căn hộ trước khi thêm phòng!")
            return
        }

        AddRoomBottomSheet.show(
            context = requireContext(),
            motelId = motelId,
            onRoomAdded = { room -> viewModel.insertRoom(room) },
            onShowToast = { message -> showToast(message) }
        )
    }

    private fun handleRoomClick(room: Room) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận thêm thông tin")
            .setMessage("Phòng này chưa có người thuê. Bạn có muốn thêm thông tin người thuê để xác nhận phòng đã cho thuê không?")
            .setNegativeButton("Không") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Thêm thông tin") { _, _ ->
                showAddInfoRoomBottomSheet(room)
            }
            .show()
    }

    private fun showAddInfoRoomBottomSheet(room: Room) {
        AddInfoRoomBottomSheet.show(
            context = requireContext(),
            room = room
        ) { updatedRoom ->
            viewModel.updateRoom(updatedRoom)
        }
    }

    override fun onClickAdapter(room: Room) {
        if (room.Rentalstatus) {
            findNavController().navigate(
                RoomManagerFragmentDirections.actionRoomManagerFragmentToDetailRoomFragment(room)
            )
        } else {
            handleRoomClick(room)
        }
    }

    private fun subListRoom(listRoom: List<Room>) = listRoom
        .groupBy { it.Floor }
        .toSortedMap()
        .map { (floor, rooms) -> FloorModel(floor, rooms) }
}
