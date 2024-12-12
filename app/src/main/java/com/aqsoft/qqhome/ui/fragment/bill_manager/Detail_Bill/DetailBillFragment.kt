package com.aqsoft.qqhome.ui.fragment.bill_manager.Detail_Bill

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.model.PaymentInfo
import com.aqsoft.qqhome.data.model.ServiceUsage
import com.aqsoft.qqhome.databinding.FragmentDetailBillBinding
import com.aqsoft.qqhome.ultis.UiHelper
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBillFragment : BaseFragment<FragmentDetailBillBinding>() {

    private val args: DetailBillFragmentArgs by navArgs()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBillBinding {
        return FragmentDetailBillBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bill = args.bill?.let { Gson().fromJson(it.DetailBill, PaymentInfo::class.java) }

        bill?.let { bindView(it) }

        bill?.services?.forEach { service ->
            if (service.oldIndex == null || service.oldIndex == 0) {
                val serviceView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.layout_usage_service2, null)
                setupViewUsageService2(serviceView, service)
                binding.containerUsageService2.addView(serviceView)
            } else {
                val serviceView =
                    LayoutInflater.from(requireContext())
                        .inflate(R.layout.layout_usage_service, null)
                setupViewUsageService(serviceView, service)
                binding.containerUsageService.addView(serviceView)
            }
        }

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViewUsageService2(serviceView: View, service: ServiceUsage) {
        val serviceNameTextView = serviceView.findViewById<TextView>(R.id.tvServiceName)
        serviceNameTextView.text = service.serviceName
        serviceNameTextView.paintFlags = serviceNameTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        serviceView.findViewById<TextView>(R.id.tvTotal).text =
            "${UiHelper.formatTextAsVND(service.cost)} đ"


    }

    private fun setupViewUsageService(view: View, service: ServiceUsage) {
        val serviceNameTextView = view.findViewById<TextView>(R.id.tvServiceName)
        serviceNameTextView.text = service.serviceName
        serviceNameTextView.paintFlags = serviceNameTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        view.findViewById<TextView>(R.id.tvOldIndex)?.text = service.oldIndex.toString()
        view.findViewById<TextView>(R.id.tvNewIndex)?.text = service.newIndex.toString()
        view.findViewById<TextView>(R.id.tvUsage)?.text = service.usage.toString()
        view.findViewById<TextView>(R.id.tvTotal)?.text =
            "${UiHelper.formatTextAsVND(service.cost)} đ"
    }

    private fun bindView(data: PaymentInfo) {
        binding.tvRoomName.text = data.roomName
        binding.tvMonthYear.text = data.Monthyear
        binding.tvCretime.text = data.cretime
        binding.tvTotalAmount.text = "Tổng tiền: ${UiHelper.formatTextAsVND(data.totalAmount)} đ"
        binding.tvPaymentsStatus.text = if (args.bill.Paymentstatus) "Đã thanh toán" else "Chưa thanh toán"
    }
}
