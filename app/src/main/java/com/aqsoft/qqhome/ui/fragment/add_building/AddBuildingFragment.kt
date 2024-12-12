package com.aqsoft.qqhome.ui.fragment.add_building

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.databinding.FragmentAddBuildingBinding
import com.aqsoft.qqhome.utils.SharedPreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class AddBuildingFragment : BaseFragment<FragmentAddBuildingBinding>() {
    private val addBuildingViewModel: AddBuildingViewModel by viewModels()
    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?
    ): FragmentAddBuildingBinding {
        return FragmentAddBuildingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindClickListeners()

    }
    private fun bindClickListeners() {
        binding.backIcon.setOnClickListener { findNavController().popBackStack() }
        binding.BtnAdd.setOnClickListener {
            if (validateInput()) {
                val currentDate = getCurrentDate()
                addBuildingViewModel.insertMotel(Motel(0, binding.edtName.text.toString(), currentDate, binding.tinh.text.toString())) { motelId ->
                    SharedPreferencesHelper.putString(requireContext(),"DefaultMotel", motelId.toString())
                    Toast.makeText(requireContext(), "Tạo nhà trọ mới thành công!", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_addBuildingFragment_to_homeFragment)
                }
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(): Boolean {
        return binding.edtName.text!!.isNotEmpty() &&
                binding.tinh.text!!.isNotEmpty()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year"
    }





}