package com.aqsoft.qqhome.data.model

data class PaymentInfo(
    val totalAmount: Int,
    val Monthyear: String,
    val roomName: String,
    val cretime: String,
    val services: List<ServiceUsage>
)
