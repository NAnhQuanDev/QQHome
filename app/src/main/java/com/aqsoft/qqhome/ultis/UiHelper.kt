package com.aqsoft.qqhome.ultis

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

object UiHelper {
    fun hideKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun formatEditTextAsVND(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true
                try {
                    val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
                    if (cleanString.isNotEmpty()) {
                        val parsed = cleanString.toLong()
                        val formatted =
                            NumberFormat.getNumberInstance(Locale("vi", "VN")).format(parsed)
                        editText.setText(formatted)
                        editText.setSelection(formatted.length)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isFormatting = false
                }
            }
        })
    }
    fun formatTextAsVND(value: Any): String {
        if(value is Long || value is Int || value is Double) {
            return NumberFormat.getNumberInstance(Locale("vi", "VN")).format(value)
        }
        return throw IllegalArgumentException("Value must be Long, Int or Double")
    }
}
