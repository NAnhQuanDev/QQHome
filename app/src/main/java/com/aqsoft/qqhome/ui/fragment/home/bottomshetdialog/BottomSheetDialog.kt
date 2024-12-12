package com.aqsoft.qqhome.ui.fragment.home.bottomshetdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.ui.fragment.home.ItemActionListener
import com.aqsoft.qqhome.ultis.Constants.DEFAULT_MOTEL
import com.aqsoft.qqhome.utils.SharedPreferencesHelper


class BottomSheetDialog(
    context: Context,
    data: List<Motel>,
    private val actionListener: ItemActionListener,
    navController: NavController
) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.bottomdialog_homefragment)

        window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.BOTTOM)
        }

        findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)

            val defaultRoomcode = SharedPreferencesHelper.getString(context, DEFAULT_MOTEL, "")?.toIntOrNull()

            val sortedData = if (defaultRoomcode != null) {
                data.sortedWith(compareBy { if (it.MotelID == defaultRoomcode) 0 else 1 })
            } else {
                data
            }

            adapter = BottomDialogAdapter(
                RoomcodeDefault = defaultRoomcode ?: -1,
                data = sortedData,
                onClick = { id ->
                    SharedPreferencesHelper.putString(context, DEFAULT_MOTEL, id.toString())
                    dismiss()
                    Toast.makeText(context, "Chuyển tòa nhà thành công", Toast.LENGTH_SHORT).show()
                },
                actionListener = actionListener
            )
        }

        findViewById<ImageView>(R.id.add)?.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addBuildingFragment)
            dismiss()
        }
    }
}

