package com.aqsoft.qqhome.data.model

data class RoomWithBillingStatus(
    val RoomID: Int,
    val RoomName: String,
    val MotelID: Int,
    val Floor: Int,
    val Rentalstatus: Boolean,
    val TenantName: String,
    val BillingStatus: String,
    val Roomprice: Long
)
