package com.aqsoft.qqhome.ui.fragment.bill_manager.adapters

import com.aqsoft.qqhome.data.entity.Bill


interface IClickBill {
    fun onClickBill(roomId : Int,roomName:String, roomprice:Long)
    fun onClickSeeBill(bill: Bill)
    fun onClickDeleteBill(bill: Bill)
    fun onClickUpdateBill(billID: Int)
}