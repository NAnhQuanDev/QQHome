package com.aqsoft.qqhome.ui.fragment.settings

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aqsoft.qqhome.base.fragments.BaseFragment
import com.aqsoft.qqhome.databinding.FragmentPolicyBinding

class PolicyFragment : BaseFragment<FragmentPolicyBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPolicyBinding {
        return FragmentPolicyBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPolicyContent()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            title = "Chính sách ứng dụng"
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupPolicyContent() {
        val policyText = SpannableStringBuilder().apply {
            // Tiêu đề chính
            appendHeading("Chính Sách Ứng Dụng QQHome")
            appendEffectiveDate("Hiệu lực từ: 01/12/2024")

            // Phần mở đầu
            append("\n\nChào mừng bạn đến với ứng dụng QQHome! Chúng tôi cam kết mang đến một công cụ quản lý nhà ở đơn giản, tiện lợi và minh bạch.\n")

            // Các phần chính sách
            appendSection(
                "1. Giới thiệu về QQHome\n",
                "QQHome là một ứng dụng quản lý nhà ở được phát triển bởi cá nhân, nhằm hỗ trợ chủ nhà quản lý thông tin cư dân, hợp đồng, chi phí và các dịch vụ liên quan một cách dễ dàng."
            )

            appendSection(
                "2. Miễn phí sử dụng\n",
                "• Ứng dụng QQHome hoàn toàn miễn phí sử dụng.\n" +
                        "• Không có bất kỳ khoản phí ẩn, phí nâng cấp hoặc chi phí dịch vụ nào."
            )
            appendSection(
                "3. Chính sách bảo mật dữ liệu\n",
                "• QQHome không thu thập, lưu trữ hoặc chia sẻ bất kỳ dữ liệu cá nhân nào của người dùng.\n" +
                        "• Mọi thông tin mà bạn nhập vào ứng dụng (như danh sách cư dân, hợp đồng, chi phí,...) chỉ được lưu trữ cục bộ trên thiết bị của bạn và không" +
                        "   được truyền tải đến bất kỳ máy chủ hoặc bên thứ ba nào."
            )
            appendSection(
                "4. Quyền riêng tư của người dùng\n",
                "• Chúng tôi tôn trọng quyền riêng tư của bạn.\n" +
                        "• Ứng dụng không yêu cầu bạn cung cấp thông tin cá nhân như email, số điện thoại hoặc thông tin tài khoản ngân hàng."
            )
            appendSection(
                "5. Quyền và trách nhiệm của người dùng\n",
                "• Người dùng chịu trách nhiệm về việc nhập và quản lý dữ liệu trên ứng dụng của mình.\n" +
                        "• Chúng tôi khuyến nghị bạn sao lưu dữ liệu định kỳ để tránh mất mát do sự cố kỹ thuật trên thiết bị."
            )
            appendSection(
                "6. Điều khoản miễn trừ trách nhiệm\n",
                "• QQHome được phát triển bởi một cá nhân với mục đích phi lợi nhuận. Chúng tôi không chịu trách nhiệm cho bất kỳ thiệt hại nào liên quan\n" +
                        "   đến việc sử dụng ứng dụng.\n" +
                        "• Ứng dụng không đảm bảo hoạt động liên tục trên mọi thiết bị hoặc phiên bản hệ điều hành."
            )
            appendSection(
                "7. Liên hệ hỗ trợ\n",
                "Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ, vui lòng liên hệ qua:\n" +
                        "• Email: nanhquan.soft@gmail.com\n" +
                        "Cảm ơn bạn đã sử dụng QQHome! Chúng tôi hy vọng ứng dụng sẽ giúp bạn quản lý nhà ở hiệu quả hơn."
            )
        }

        binding.tvPolicy.text = policyText
    }

    private fun SpannableStringBuilder.appendHeading(text: String): SpannableStringBuilder {
        val start = length
        append(text)
        setSpan(
            StyleSpan(Typeface.BOLD),
            start, length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(
            RelativeSizeSpan(1.5f),
            start, length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        append("\n")
        return this
    }

    private fun SpannableStringBuilder.appendEffectiveDate(text: String): SpannableStringBuilder {
        val start = length
        append(text)
        setSpan(
            StyleSpan(Typeface.ITALIC),
            start, length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        append("\n")
        return this
    }

    private fun SpannableStringBuilder.appendSection(
        title: String,
        content: String
    ): SpannableStringBuilder {
        // Tiêu đề phần
        val titleStart = length
        append("\n$title\n")
        setSpan(
            StyleSpan(Typeface.BOLD),
            titleStart, length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Nội dung phần
        append(content)
        append("\n")
        return this
    }
}