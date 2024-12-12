package com.aqsoft.qqhome.ui.fragment.home.bottomshetdialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Motel
import com.aqsoft.qqhome.databinding.ItemListmotelBinding
import com.aqsoft.qqhome.ui.fragment.home.ItemActionListener


class BottomDialogAdapter(
    private val RoomcodeDefault:Int,
    private val data: List<Motel>,
    private val onClick: (Int) -> Unit,
    private val actionListener: ItemActionListener

) : RecyclerView.Adapter<BottomDialogAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemListmotelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListmotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val motel = data[position]
        holder.binding.apply {
            if(RoomcodeDefault != motel.MotelID) {
                layoutVideo.setBackgroundResource(R.drawable.backgroud_rounded2)
                delete.setOnClickListener{
                    actionListener.onDeleteItem(motel.MotelID)
                }
                delete.visibility = View.VISIBLE
            }
            addressmotel.text = motel.Address
            namemotel.text = motel.NameMotel
            root.setOnClickListener {
                onClick(motel.MotelID)
            }


        }
    }

    override fun getItemCount(): Int = data.size
}

