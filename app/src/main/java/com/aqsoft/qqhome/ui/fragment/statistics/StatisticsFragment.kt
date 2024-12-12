package com.aqsoft.qqhome.ui.fragment.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.databinding.FragmentStatisticsBinding
import com.aqsoft.qqhome.ultis.Constants
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {
    private val viewModel: StatisticsViewModel by viewModels()
    private var MotelID: Int = 0
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatisticsBinding {
        return FragmentStatisticsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMotelID()
        getTime()
        getData(selectedMonth, selectedYear)
        setupListeners()
    }

    private fun setupListeners() {
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.pickDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun getTime() {
        val calendar = java.util.Calendar.getInstance()
        selectedMonth = calendar.get(java.util.Calendar.MONTH) + 1
        selectedYear = calendar.get(java.util.Calendar.YEAR)
        binding.tvTime.text = "Tháng $selectedMonth, $selectedYear"
    }

    private fun getMotelID() {
        MotelID = SharedPreferencesHelper.getString(requireContext(), Constants.DEFAULT_MOTEL, "")!!
            .toInt()
    }

    private fun getData(month: Int, year: Int) {

        viewModel.getRoomCountByMotelIdAndRentalStatus(MotelID).observe(viewLifecycleOwner) {
            binding.tvTotalRoomsRented.text = "(${it} đang được thuê)"
        }


        viewModel.getRoomCountByMotelId(MotelID).observe(viewLifecycleOwner) {
            binding.tvTotalRooms.text = "Tổng số phòng: ${it}"
        }


        viewModel.getRoomNotHaveBill(MotelID, "${month}/${year}").observe(viewLifecycleOwner) {
            binding.tvNotHaveBills.text = "Chưa chốt hóa đơn: ${it.size}"
        }

        var paid = -1
        var unpaid = -1


        viewModel.getPaidBills("${month}/${year}", MotelID) { data ->
            paid = data
            binding.tvPaidBills.text = "Đã thanh toán: $data"
            if (unpaid != -1 && paid != -1) {
                LoadpieChart(paid, unpaid)
            }
        }
        viewModel.getUnpaidBills("${month}/${year}", MotelID) { data ->
            unpaid = data
            binding.tvUnpaidBills.text = "Chưa thanh toán: $data"
            if (unpaid != -1 && paid != -1) {
                LoadpieChart(paid, unpaid)
            }
        }
    }

    private fun LoadpieChart(paid: Int, unpaid: Int) {
        setupPieChart(paid, unpaid)
    }

    private fun setupPieChart(paid: Int, unpaid: Int) {
        val pieChart = binding.pieChart
        val description = Description().apply {
            text = "Tỷ lệ thanh toán hóa đơn"
            textSize = 12f
        }
        pieChart.description = description

        pieChart.setUsePercentValues(true)
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        val total = paid + unpaid

        if (total == 0) {
            val entries = listOf(
                PieEntry(1f, "Không có dữ liệu")
            )
            val dataSet = PieDataSet(entries, "Hóa đơn").apply {
                sliceSpace = 3f
                selectionShift = 5f
                colors = listOf(
                    ContextCompat.getColor(requireContext(), R.color.gray)
                )
            }

            val data = PieData(dataSet).apply {
                setDrawValues(false)
            }

            pieChart.data = data
        } else {
            val entries = mutableListOf<PieEntry>()
            if (paid > 0) {
                entries.add(PieEntry(paid.toFloat(), "Đã thanh toán"))
            }
            if (unpaid > 0) {
                entries.add(PieEntry(unpaid.toFloat(), "Chưa thanh toán"))
            }
            val dataSet = PieDataSet(entries, "Hóa đơn").apply {
                sliceSpace = 3f
                selectionShift = 5f
                colors = listOf(
                    ContextCompat.getColor(requireContext(), R.color.green),
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
            }
            val data = PieData(dataSet).apply {
                setDrawValues(true)
                setValueFormatter(PercentFormatter(pieChart))
                setValueTextSize(12f)
                setValueTextColor(Color.BLACK)
            }

            pieChart.data = data
        }
        pieChart.animateY(1400, com.github.mikephil.charting.animation.Easing.EaseInOutQuad)
        pieChart.invalidate()
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

            getData(selectedMonth, selectedYear)

            dialog.dismiss()
        }
    }


}
