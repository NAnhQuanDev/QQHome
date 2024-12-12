package com.aqsoft.qqhome.ui.fragment.bill_manager.Finalize_Bill

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.entity.Bill
import com.aqsoft.qqhome.data.entity.Service
import com.aqsoft.qqhome.databinding.FragmentFinalizeBillBinding
import com.aqsoft.qqhome.ultis.Constants
import com.aqsoft.qqhome.ultis.UiHelper
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FinalizeBillFragment : BaseFragment<FragmentFinalizeBillBinding>() {

    private val viewModel: FinalizeBillViewModel by viewModels()
    private val args: FinalizeBillFragmentArgs by navArgs()
    private var totalSum: Long = 0L

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinalizeBillBinding {
        return FragmentFinalizeBillBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeServices()
    }

    private fun setupUI() {
        with(binding) {
            btnSave.setOnClickListener { saveBill() }
            backIcon.setOnClickListener { findNavController().popBackStack() }


            roomName.text = "Phòng: ${args.roomName}"
            time.text = "Hóa đơn của tháng: ${args.selectedMonth}/${args.selectedYear}"
            priceroom.text = "Giá phòng: ${UiHelper.formatTextAsVND(args.roomPrice)} đ"
        }
    }

    private fun observeServices() {
        viewModel.getAllService(SharedPreferencesHelper.getString(requireContext(),Constants.DEFAULT_MOTEL,"")!!.toInt()).observe(viewLifecycleOwner) { services ->
            services.forEach { service ->
                when (service.ServiceType.name) {
                    "Monthly" -> addServiceView(binding.containerOtherServices, service, true)
                    "PER_INDEX" -> addServiceView(binding.containerServices, service, false)
                }
            }
            updateTotalSum()
        }
    }

    private fun addServiceView(container: ViewGroup, service: Service, isMonthly: Boolean) {
        val serviceView = layoutInflater.inflate(
            R.layout.layout_calculate_bill,
            container,
            false
        )
        if (isMonthly) {
            setupMonthlyServiceView(serviceView, service)
        } else {
            setupIndexBasedServiceView(serviceView, service)
        }
        container.addView(serviceView)
    }

    private fun setupMonthlyServiceView(view: View, service: Service) {
        with(view) {
            findViewById<TextView>(R.id.title).text = "Dịch vụ: ${service.ServiceName}"
            findViewById<TextView>(R.id.tvTotal).text =
                "Thành tiền: ${UiHelper.formatTextAsVND(service.ServiceCost)} đ"
            findViewById<View>(R.id.view).isVisible = true
            findViewById<TextInputEditText>(R.id.edtElectricnew).isVisible = false
            findViewById<TextInputEditText>(R.id.edtElectricold).isVisible = false
            findViewById<TextView>(R.id.tvElectricUsed).isVisible = false
        }
    }

    private fun setupIndexBasedServiceView(view: View, service: Service) {
        with(view) {
            val title = findViewById<TextView>(R.id.title)
            val edtOld = findViewById<TextInputEditText>(R.id.edtElectricold)
            val edtNew = findViewById<TextInputEditText>(R.id.edtElectricnew)
            val tvUsage = findViewById<TextView>(R.id.tvElectricUsed)
            val tvTotal = findViewById<TextView>(R.id.tvTotal)
            val tvElectricPrice = findViewById<TextView>(R.id.tvElectricPrice)

            tvElectricPrice.text = "Phí mỗi đơn vị: ${UiHelper.formatTextAsVND(service.ServiceCost)} đ"

            title.text = "Dịch vụ: ${service.ServiceName}"

            setupUsageCalculation(edtOld, edtNew, tvUsage, tvTotal, service)
        }
    }

    private fun setupUsageCalculation(
        edtOld: TextInputEditText,
        edtNew: TextInputEditText,
        tvUsage: TextView,
        tvTotal: TextView,

        service: Service
    ) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateUsage(edtOld, edtNew, tvUsage, tvTotal, service)
            }
        }

        edtOld.addTextChangedListener(textWatcher)
        edtNew.addTextChangedListener(textWatcher)
    }

    private fun calculateUsage(
        edtOld: TextInputEditText,
        edtNew: TextInputEditText,
        tvUsage: TextView,
        tvTotal: TextView,
        service: Service
    ) {
        try {
            val oldValue = edtOld.text.toString().toLongOrNull() ?: 0L
            val newValue = edtNew.text.toString().toLongOrNull() ?: 0L

            if (newValue > oldValue) {
                val usage = newValue - oldValue
                val totalCost = usage * service.ServiceCost

                tvUsage.text = "Tiêu thụ: $usage ${service.ServiceUnit}"
                tvTotal.text = "Thành tiền: ${UiHelper.formatTextAsVND(totalCost)} đ"
            } else {
                tvUsage.text = "Tiêu thụ: 0"
                tvTotal.text = "Thành tiền: 0 đ"
            }
            updateTotalSum()
        } catch (e: Exception) {
            showToast("Có lỗi xảy ra")
            e.printStackTrace()
        }
    }

    private fun saveBill() {
        val bill = Bill(
            Total = totalSum.toDouble(),
            RoomName = args.roomName,
            RoomID = args.roomId,
            cretime = getCurrentTime(),
            Monthyear = "${args.selectedMonth}/${args.selectedYear}",
            Paymentstatus = false,
            MotelID = SharedPreferencesHelper.getString(
                requireContext(),
                Constants.DEFAULT_MOTEL,
                ""
            )!!.toInt(),
            PaidDate = "",
            BillingStatus = true,
            DetailBill = generateDetailBill()
        )

        viewModel.insertBill(bill)
        showToast("Hóa đơn đã được lưu")
        findNavController().popBackStack()
    }

    private fun generateDetailBill(): String {
        val services = mutableListOf<Map<String, Any>>()

        services.add(
            mapOf(
                "serviceName" to "Tiền phòng",
                "cost" to args.roomPrice
            )
        )

        addServicesToDetail(binding.containerServices, services)
        addServicesToDetail(binding.containerOtherServices, services)

        val billDetails = mapOf(
            "roomName" to args.roomName,
            "cretime" to getCurrentTime(),
            "Monthyear" to "${args.selectedMonth}/${args.selectedYear}",
            "totalAmount" to totalSum,
            "services" to services
        )

        return Gson().toJson(billDetails)
    }

    private fun addServicesToDetail(container: ViewGroup, services: MutableList<Map<String, Any>>) {
        container.children.forEach { view ->
            val serviceName =
                view.findViewById<TextView>(R.id.title)?.text?.toString()?.substringAfter(": ")
                    ?: ""
            val oldIndex =
                view.findViewById<TextInputEditText>(R.id.edtElectricold)?.text?.toString()
                    ?.toLongOrNull() ?: 0
            val newIndex =
                view.findViewById<TextInputEditText>(R.id.edtElectricnew)?.text?.toString()
                    ?.toLongOrNull() ?: 0
            val usage = newIndex - oldIndex
            val total = view.findViewById<TextView>(R.id.tvTotal)?.text?.toString()
                ?.replace("[^\\d]".toRegex(), "")?.toLongOrNull() ?: 0

            if (serviceName.isNotBlank() && (usage > 0 || total > 0)) {
                services.add(
                    mapOf(
                        "serviceName" to serviceName,
                        "oldIndex" to oldIndex,
                        "newIndex" to newIndex,
                        "usage" to usage,
                        "cost" to total

                    )
                )
            }
        }
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("M/yyyy", Locale.getDefault()).format(Date())
    }

    private fun updateTotalSum() {
        totalSum = args.roomPrice + calculateServicesTotal()
        binding.tvTotalAmount.text = "Tổng tiền: ${UiHelper.formatTextAsVND(totalSum)} đ"
    }

    private fun calculateServicesTotal(): Long {
        return binding.containerServices.children.mapNotNull { child ->
            child.findViewById<TextView>(R.id.tvTotal)
                ?.text
                ?.toString()
                ?.replace("[^\\d]".toRegex(), "")
                ?.toLongOrNull()
        }.sum() + binding.containerOtherServices.children.mapNotNull { child ->
            child.findViewById<TextView>(R.id.tvTotal)
                ?.text
                ?.toString()
                ?.replace("[^\\d]".toRegex(), "")
                ?.toLongOrNull()
        }.sum()
    }
}
