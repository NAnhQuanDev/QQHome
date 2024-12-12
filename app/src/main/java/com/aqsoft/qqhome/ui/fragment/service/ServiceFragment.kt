package com.aqsoft.qqhome.ui.fragment.service

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.entity.Service
import com.aqsoft.qqhome.data.model.ServiceType
import com.aqsoft.qqhome.databinding.FragmentServiceBinding
import com.aqsoft.qqhome.ui.fragment.service.adapter.IClickService
import com.aqsoft.qqhome.ui.fragment.service.adapter.IconAdapter
import com.aqsoft.qqhome.ui.fragment.service.adapter.ServiceAdapter
import com.aqsoft.qqhome.ultis.Constants
import com.aqsoft.qqhome.ultis.UiHelper
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceFragment : BaseFragment<FragmentServiceBinding>(), IClickService {
    private val viewModel: ServiceViewModel by viewModels()
    private lateinit var dataListService: List<Service>

    private val preSetIcon: Int = R.drawable.select
    private var selectedIcon: Int? = null

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentServiceBinding {
        return FragmentServiceBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerView()
        observeData()
    }

    private fun setupListeners() {
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.add.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetStyle)
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.bottomshet_add_service, null)
        dialog.setContentView(dialogView)
        dialog.window?.setWindowAnimations(0)
        dialog.setCancelable(true)
        dialog.behavior.skipCollapsed = true
        dialog.behavior.isDraggable = true
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        dialog.show()

        val nameService = dialogView.findViewById<TextInputEditText>(R.id.NameService)
        val selection = dialogView.findViewById<ImageView>(R.id.selection)
        val spinner: Spinner = dialogView.findViewById(R.id.serviceTypeSpinner)
        val priceService = dialogView.findViewById<TextInputEditText>(R.id.PriceService)
        val UnitSerivce = dialogView.findViewById<TextInputEditText>(R.id.UnitSerivce)
        UiHelper.formatEditTextAsVND(priceService)
        val btnSave = dialogView.findViewById<Button>(R.id.btnSave)

        val iconList = listOf(
            R.drawable.icon_service_1,
            R.drawable.icon_service_2,
            R.drawable.icon_service_3,
            R.drawable.icon_service_4,
            R.drawable.icon_service_5,
            R.drawable.icon_service_6,
            R.drawable.icon_service_7,
            R.drawable.icon_service_8,
            R.drawable.icon_service_9,
            R.drawable.icon_service_10,
            R.drawable.icon_service_11,
            R.drawable.icon_service_12,
        )

        selection.setImageResource(preSetIcon)

        selectedIcon = null

        selection.setOnClickListener {
            showIconPickerDialog(iconList, selection)
        }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            ServiceType.values().map { it.displayName }
        )
        spinner.adapter = spinnerAdapter

        btnSave.setOnClickListener {
            val motelId =
                SharedPreferencesHelper.getString(requireContext(), Constants.DEFAULT_MOTEL, "")!!.toInt()

            val selectedTypeIndex = spinner.selectedItemPosition
            val selectedType = ServiceType.values()[selectedTypeIndex]

            val serviceName = nameService.text.toString()
            val serviceCost = priceService.text.toString().replace(".", "").replace("₫", "").toIntOrNull()

            if (serviceName.isBlank() || serviceCost == null) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val iconToSave = selectedIcon ?: R.drawable.ic_input

            val service = Service(
                MotelID = motelId,
                Icon = iconToSave.toString(),
                ServiceType = selectedType,
                ServiceCost = serviceCost.toLong(),
                ServiceName = serviceName,
                ServiceUnit = if(UnitSerivce.text.toString().isBlank()) "" else UnitSerivce.text.toString()
            )
            viewModel.insertService(service)
            nameService.text?.clear()
            priceService.text?.clear()
            spinner.setSelection(0)

            selectedIcon = null

            selection.setImageResource(preSetIcon)

            dialog.dismiss()
        }
    }

    private fun showIconPickerDialog(listIcon: List<Int>, imgSelectedIcon: ImageView) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_icon_picker, null)
        val gridView = dialogView.findViewById<GridView>(R.id.gridIcon)

        val adapter = IconAdapter(requireContext(), listIcon)
        gridView.adapter = adapter

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Chọn Icon")
            .setView(dialogView)
            .create()

        gridView.setOnItemClickListener { _, _, position, _ ->
            val icon = listIcon[position]
            imgSelectedIcon.setImageResource(icon)
            selectedIcon = icon
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        viewModel.getAllService(
            SharedPreferencesHelper.getString(requireContext(), Constants.DEFAULT_MOTEL, "")!!.toInt()
        ).observe(viewLifecycleOwner) { data ->
            dataListService = data
            binding.rv.adapter = ServiceAdapter(dataListService, this)
        }
    }

    override fun onClickService(service: Service) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc chắn muốn xóa dịch vụ này không?")
            .setPositiveButton("Xóa") { dialog, _ ->
                viewModel.deleteService(service)
                dialog.dismiss()
            }
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
