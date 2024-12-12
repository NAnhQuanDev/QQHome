package com.aqsoft.qqhome.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Motel(
    @PrimaryKey(autoGenerate = true)
    val MotelID:Int,
    @ColumnInfo
    val NameMotel: String,
    @ColumnInfo
    val CreTime: String,
    @ColumnInfo
    val Address: String


    )

