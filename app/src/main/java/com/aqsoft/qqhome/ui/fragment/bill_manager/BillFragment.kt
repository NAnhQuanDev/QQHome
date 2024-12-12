package com.aqsoft.qqhome.ui.fragment.bill_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.entity.Bill
import com.aqsoft.qqhome.databinding.FragmentBillBinding
import com.aqsoft.qqhome.ui.fragment.bill_manager.adapters.FinalizedBillAdapter
import com.aqsoft.qqhome.ui.fragment.bill_manager.adapters.IClickBill
import com.aqsoft.qqhome.ui.fragment.bill_manager.adapters.PendingBillAdapter
import com.aqsoft.qqhome.ultis.Constants
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BillFragment : BaseFragment<FragmentBillBinding>(), IClickBill {

    private val billViewmodel: BillViewModel by viewModels()

    private var selectedTabPosition = 0
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBillBinding {
        return FragmentBillBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        setupTabLayout()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        selectedMonth = calendar.get(Calendar.MONTH) + 1
        selectedYear = calendar.get(Calendar.YEAR)
        binding.tvTime.text = "Tháng $selectedMonth, $selectedYear"

        savedInstanceState?.let {
            selectedTabPosition = it.getInt("selected_tab", 0)
        }

        binding.tabLayout.getTabAt(selectedTabPosition)?.select()
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        loadRoomData(selectedTabPosition)

        binding.pickDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chưa chốt"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đã chốt"))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTabPosition = tab?.position ?: 0
                loadRoomData(selectedTabPosition)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadRoomData(tabPosition: Int) {
        val time = "$selectedMonth/$selectedYear"
        val motelId =
            SharedPreferencesHelper.getString(requireContext(), Constants.DEFAULT_MOTEL, "")
                ?.toInt() ?: return

        if (tabPosition == 0) {
            billViewmodel.getRoomNotHaveBill(motelId, time).observe(viewLifecycleOwner) { data ->
                val adapter = PendingBillAdapter(data, selectedMonth, selectedYear, this)
                binding.rv.layoutManager = LinearLayoutManager(context)
                binding.rv.adapter = adapter
            }
        } else {
            billViewmodel.getBillByMonthYear(motelId, time).observe(viewLifecycleOwner) { data ->
                val adapter = FinalizedBillAdapter(data, this)
                binding.rv.layoutManager = LinearLayoutManager(context)
                binding.rv.adapter = adapter
            }
        }
    }

    private fun showDatePickerDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_pickerdate, null)
        val pickerMonth = dialogView.findViewById<NumberPicker>(R.id.picker_month)
        val pickerYear = dialogView.findViewById<NumberPicker>(R.id.picker_year)
        val btnSave = dialogView.findViewById<Button>(R.id.btnSave)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        setupDatePicker(dialog, pickerMonth, pickerYear, btnSave)
        dialog.show()
    }

    private fun setupDatePicker(
        dialog: androidx.appcompat.app.AlertDialog,
        pickerMonth: NumberPicker,
        pickerYear: NumberPicker,
        btnSave: Button
    ) {
        pickerMonth.minValue = 1
        pickerMonth.maxValue = 12
        pickerMonth.value = selectedMonth
        pickerMonth.wrapSelectorWheel = false

        pickerYear.minValue = 2020
        pickerYear.maxValue = 2100
        pickerYear.value = selectedYear
        pickerYear.wrapSelectorWheel = false

        btnSave.setOnClickListener {
            selectedMonth = pickerMonth.value
            selectedYear = pickerYear.value
            binding.tvTime.text = "Tháng $selectedMonth, $selectedYear"

            loadRoomData(selectedTabPosition)

            dialog.dismiss()
        }
    }

    override fun onClickBill(roomId: Int, roomName: String, roomPrice: Long) {
        val action = BillFragmentDirections.actionBillFragmentToFinalizeBillFragment(
            roomId,
            roomName,
            roomPrice,
            selectedMonth,
            selectedYear
        )
        findNavController().navigate(action)
    }

    override fun onClickSeeBill(bill: Bill) {
        val action = BillFragmentDirections.actionBillFragmentToDetailBillFragment(
            bill
        )
        findNavController().navigate(action)

    }

    override fun onClickDeleteBill(bill: Bill) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc chắn muốn xóa hóa đơn này?")
            .setPositiveButton("Xóa") { _, _ ->
                billViewmodel.deleteBill(bill)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onClickUpdateBill(billID: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận")
            .setMessage("Bạn sẽ xác nhận rằng hóa đơn này đã được thanh toán?")
            .setPositiveButton("Xác nhận") { _, _ ->
                billViewmodel.updateBill(billID, true)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_tab", selectedTabPosition)
    }
}
