package com.aqsoft.qqhome.ui.fragment.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.databinding.FragmentSettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()

    }

    private fun setListener() {
        binding.chinhsach.setOnClickListener {findNavController().navigate(R.id.action_settingFragment_to_polictyFragment)}
    }



}