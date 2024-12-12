package com.aqsoft.qqhome.ui.fragment.service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Service
import com.aqsoft.qqhome.ultis.UiHelper

class ServiceAdapter(
    private val data: List<Service>,
    private val iClickService: IClickService
) : RecyclerView.Adapter<ServiceAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tendichvu: TextView = itemView.findViewById(R.id.serviceName)
        val giatien: TextView = itemView.findViewById(R.id.servicePrice)
        val type: TextView = itemView.findViewById(R.id.serviceType)
        val unit : TextView = itemView.findViewById(R.id.serviceUnit)
        val serviceIcon: ImageView = itemView.findViewById(R.id.serviceIcon)
        val delete: ImageView = itemView.findViewById(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tendichvu.text = data[position].ServiceName
        holder.serviceIcon.setImageResource(data[position].Icon.toInt())
        holder.giatien.text = "Phí: ${UiHelper.formatTextAsVND(data[position].ServiceCost)} đ"
        holder.type.text =  data[position].ServiceType.displayName
        holder.unit.text = data[position].ServiceUnit
        holder.delete.setOnClickListener {
            iClickService.onClickService(data[position])
        }


    }
}
