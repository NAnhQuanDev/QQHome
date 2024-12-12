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
            entity = Motel::class,
            parentColumns = ["MotelID"],
            childColumns = ["MotelID"],
            onDelete = ForeignKey.CASCADE
        )

    ]
)
@Parcelize
data class Room(
    @PrimaryKey(autoGenerate = true)
    val RoomID: Int = 0,
    @ColumnInfo
    val RoomName: String,
    @ColumnInfo
    val MotelID: Int,
    @ColumnInfo
    val Floor: Int,
    @ColumnInfo
    val Roomprice: Long,
    @ColumnInfo
    val Rentalstatus: Boolean,
    @ColumnInfo
    val TenantName: String,
    @ColumnInfo
    val TenantPhone: String,
    @ColumnInfo
    val Rentalstartdate: String
) : Parcelable
