package com.aqsoft.qqhome.ui.fragment.feedback

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.databinding.FragmentFeedBackBinding

class FeedBackFragment : BaseFragment<FragmentFeedBackBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedBackBinding {
        return FragmentFeedBackBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llMessenger.setOnClickListener {
            copyToClipboard("https://www.facebook.com/Quandzai.Info")
            disableButtonForDelay(binding.llMessenger)
        }
        binding.llZalo.setOnClickListener {
            copyToClipboard("0394309941")
            disableButtonForDelay(binding.llZalo)
        }
    }

    private fun copyToClipboard(content: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = android.content.ClipData.newPlainText("", content)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Đã sao chép vào bộ nhớ tạm", Toast.LENGTH_SHORT).show()
    }

    private fun disableButtonForDelay(view: View) {
        view.isEnabled = false
        Handler().postDelayed({
            view.isEnabled = true
        }, 2000)
    }
}
