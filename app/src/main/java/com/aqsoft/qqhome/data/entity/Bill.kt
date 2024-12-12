package com.aqsoft.qqhome.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Room::class,
            parentColumns = ["RoomID"],
            childColumns = ["RoomID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Motel::class,
            parentColumns = ["MotelID"],
            childColumns = ["MotelID"],
            onDelete = ForeignKey.CASCADE
        )

    ]

)
@Parcelize
data class Bill(
    @PrimaryKey (autoGenerate = true)
    val BillID: Int = 0,
    @ColumnInfo
    val RoomID: Int,
    @ColumnInfo
    val RoomName: String, // tên phòng
    @ColumnInfo
    val cretime: String, // thời gian tạo bill
    @ColumnInfo
    val MotelID: Int, // id của nhà trọ
    @ColumnInfo
    val Monthyear: String, // tháng năm
    @ColumnInfo
    val Paymentstatus: Boolean, // trạng thái thanh toán(chưa hay rồi)
    @ColumnInfo
    val PaidDate: String, // thời gian đã thanh toán
    @ColumnInfo
    val BillingStatus: Boolean, //trạng thái của hóa ơn
    @ColumnInfo
    val DetailBill: String, //Chi tiết hóa đơn lưu các dịch vụ đã sử dụng với tiền, column lưu dạng string đỡ tạo thêm bảng
    @ColumnInfo
    val Total: Double, // tổng tiền

): Parcelable
