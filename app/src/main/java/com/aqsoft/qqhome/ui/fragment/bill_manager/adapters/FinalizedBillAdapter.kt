package com.aqsoft.qqhome.ui.fragment.bill_manager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aqsoft.qqhome.R
import com.aqsoft.qqhome.data.entity.Bill
import com.aqsoft.qqhome.ultis.UiHelper.formatTextAsVND
import com.google.android.material.button.MaterialButton

class FinalizedBillAdapter(private val data: List<Bill>, private val lisener: IClickBill) :
    RecyclerView.Adapter<FinalizedBillAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val huy: MaterialButton = itemView.findViewById(R.id.huy)
        val xacnhan: MaterialButton = itemView.findViewById(R.id.xacnhan)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_finalized_bill, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = data[position]
        with(holder) {
            tvRoomName.text = bill.RoomName
            tvStatus.text = if (bill.Paymentstatus) "Đã thanh toán" else "Chưa thanh toán"
            tvStatus.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (bill.Paymentstatus) R.color.green else R.color.red
                )
            )
            if (bill.Paymentstatus) {
                huy.visibility = View.GONE
                xacnhan.visibility = View.GONE
            } else {
                huy.visibility = View.VISIBLE
                xacnhan.visibility = View.VISIBLE
                huy.setOnClickListener { lisener.onClickDeleteBill(bill) }
                xacnhan.setOnClickListener { lisener.onClickUpdateBill(bill.BillID) }
            }

            tvTime.text = "Thời gian: ${bill.Monthyear}"
            tvAmount.text = "${formatTextAsVND(bill.Total)} VND"

            itemView.setOnClickListener { lisener.onClickSeeBill(bill) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
