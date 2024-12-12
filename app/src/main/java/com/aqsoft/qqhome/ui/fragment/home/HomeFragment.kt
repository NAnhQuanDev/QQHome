package com.aqsoft.qqhome.ui.fragment.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.databinding.FragmentHomeBinding
import com.aqsoft.qqhome.ui.fragment.home.bottomshetdialog.BottomSheetDialog
import com.aqsoft.qqhome.ultis.Constants
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ItemActionListener {

    private var motelID: String = ""
    private var motelData: Motel? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val homeViewModel: HomeViewModel by viewModels()
    private var bottomSheetDialog: BottomSheetDialog? = null
    private val sharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == Constants.DEFAULT_MOTEL) {
                getData()
            }
        }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = SharedPreferencesHelper.getInstance(requireContext())
        getData()
        setupClickListeners()
    }

    private fun getData() {
        motelID = sharedPreferences.getString("DefaultMotel", "") ?: ""
        if (motelID.isEmpty() || !motelID.all { it.isDigit() }) {
            safelyNavigate(R.id.addBuildingFragment)
            return
        }

        homeViewModel.getMotelByID(motelID.toInt()) { data ->
            if (data == null) {
                findNavController().popBackStack()
               findNavController().navigate(R.id.addBuildingFragment
               )
            } else {
                motelData = data
                bindData()
            }
        }
    }

    private fun bindData() {
        val motel = motelData ?: return
        binding.Tentro.text = "Chủ nhà, ${motel.NameMotel}"
        homeViewModel.getRoomCountByMotelId(motelID.toInt()).observe(viewLifecycleOwner) { count ->
            binding.soluongphong.text = count.toString()
        }
        homeViewModel.getRoomCountByMotelIdAndRentalStatus(motelID.toInt())
            .observe(viewLifecycleOwner) { count ->
                binding.sophongchothue.text = count.toString()
            }
        homeViewModel.getServiceCount(motelID.toInt()).observe(viewLifecycleOwner) { count ->
            binding.soluongdichvu.text = count.toString()
        }
        homeViewModel.getRoomCountUnRental(motelID.toInt()).observe(viewLifecycleOwner) { count ->
            binding.sophongtrong.text = count.toString()
        }

    }

    private fun setupClickListeners() {
        binding.quanly.setOnClickListener {
            safelyNavigate(R.id.action_homeFragment_to_roomManagerFragment)
        }

        binding.title.setOnClickListener {
            homeViewModel.getAllMotel { motelList ->
                bottomSheetDialog =
                    BottomSheetDialog(requireContext(), motelList, this, findNavController())
                bottomSheetDialog?.show()
            }
        }

        binding.dichvu.setOnClickListener {
            safelyNavigate(R.id.action_homeFragment_to_serviceFragment)
        }

        binding.hoadon.setOnClickListener {
            safelyNavigate(R.id.action_homeFragment_to_billFragment)
        }
        binding.thongke.setOnClickListener {
            safelyNavigate(R.id.action_homeFragment_to_statisticsFragment)
        }
    }



    override fun onResume() {
        super.onResume()
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    override fun onDeleteItem(itemId: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc chắn muốn xóa, dữ liêu sẽ không thể khôi phục sau thao tác này")
            .setPositiveButton("Xóa") { dialog, _ ->
                homeViewModel.deleteMotelById(itemId)
                showToast("Xóa thành công")
                bottomSheetDialog?.dismiss()
            }

            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    private fun safelyNavigate(destinationId: Int) {
        if (isAdded && findNavController().currentDestination?.id == R.id.homeFragment) {
            findNavController().navigate(destinationId)
        }
    }
}
