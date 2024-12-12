package com.aqsoft.qqhome.data.model

import com.aqsoft.qqhome.data.entity.Room


data class FloorModel(
    val FloorNumber: Int,
    val listRoom: List<Room>
)
