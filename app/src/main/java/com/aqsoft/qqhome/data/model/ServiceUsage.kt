package com.aqsoft.qqhome.data.model

data class ServiceUsage (
    val serviceName: String,
    val cost: Int,
    val oldIndex: Int? = null,
    val newIndex: Int? = null,
    val usage: Int? = null

)