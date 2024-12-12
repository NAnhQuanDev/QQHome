package com.aqsoft.qqhome.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.utils.SharedPreferencesHelper


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBasedOnCondition()

    }
    private fun checkMotel(): Boolean {
        return SharedPreferencesHelper.getString(requireContext(),"DefaultMotel").isNullOrEmpty()
    }

    private fun navigateBasedOnCondition() {
       if (checkMotel()) {
            findNavController().navigate(R.id.action_splashFragment_to_AddBuildingFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

        }
    }


}