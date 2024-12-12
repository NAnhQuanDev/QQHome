package com.aqsoft.qqhome.ui.fragment.room_manager.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.databinding.ItemRoomBinding
import com.aqsoft.qqhome.data.entity.Room

class RoomManagerAdapter(
    private var data: List<Room>,
    private val listener: IClickRoomAdapter
) : RecyclerView.Adapter<RoomManagerAdapter.MyViewHolder>() {

    private val originalData: List<Room> = ArrayList(data) // Lưu danh sách đầy đủ để khôi phục khi cần

    class MyViewHolder(binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: ItemRoomBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRoomBinding = ItemRoomBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val room = data[position]
        if (room.Rentalstatus == true) {
            holder.binding.img.setImageResource(com.aqsoft.qqhome.R.drawable.room_close)
        }
        holder.binding.tvRoomCode.text = room.RoomName
        holder.itemView.setOnClickListener { listener.onClickAdapter(room) }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun filterList(query: String) {
        data = if (query.isEmpty()) {
            originalData // Nếu không có query, hiển thị danh sách đầy đủ
        } else {
            originalData.filter { room ->
                room.RoomName.contains(query, ignoreCase = true) // Lọc theo RoomName (không phân biệt hoa thường)
            }
        }
        notifyDataSetChanged()
    }
}
