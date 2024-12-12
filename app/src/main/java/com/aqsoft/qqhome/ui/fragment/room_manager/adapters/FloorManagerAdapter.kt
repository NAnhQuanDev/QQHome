package com.aqsoft.qqhome.ui.fragment.room_manager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.model.FloorModel


class FloorManagerAdapter(private val data: List<FloorModel>, private val listener: IClickRoomAdapter) : RecyclerView.Adapter<FloorManagerAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFloor: TextView = view.findViewById(R.id.tvFloor)
        val rvRoom: RecyclerView = view.findViewById(R.id.rvRoom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_floor, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {return data.size}

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listFloor: FloorModel = data[position]
        holder.tvFloor.text = "Tầng ${listFloor.FloorNumber}"
        if (holder.rvRoom.layoutManager == null) {
            holder.rvRoom.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        }
        val adapter = RoomManagerAdapter(listFloor.listRoom,listener)
        holder.rvRoom.adapter = adapter
    }
}

