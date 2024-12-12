package com.aqsoft.qqhome.ui.fragment.bill_manager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Room
import com.aqsoft.qqhome.data.model.RoomWithBillingStatus
import com.aqsoft.qqhome.ultis.UiHelper.formatTextAsVND

class PendingBillAdapter(
    private val data: List<Room>, private val month: Int, private val years: Int,
    private val lisenner: IClickBill
) : RecyclerView.Adapter<PendingBillAdapter.BillViewHolder>() {



    class BillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pending_bill, parent, false)
        return BillViewHolder(view)

    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.tvRoomName.text = data[position].RoomName
        holder.tvStatus.text = "Chưa chốt hóa đơn"
        holder.tvAmount.text = formatTextAsVND(data[position].Roomprice) + " VND"
        holder.itemView.setOnClickListener {
            lisenner.onClickBill(
                data[position].RoomID,
                data[position].RoomName,
                data[position].Roomprice
            )
        }
        holder.tvTime.text = "Tháng $month, $years"

    }

    override fun getItemCount(): Int {
        return data.size
    }

}