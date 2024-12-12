package com.aqsoft.qqhome.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.aqsoft.qqhome.data.model.ServiceType
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
data class Service(
    @PrimaryKey(autoGenerate = true)
    val ServiceID: Int =0,
    @ColumnInfo
    val ServiceName:String,
    @ColumnInfo
    val Icon:String,
    @ColumnInfo
    val ServiceType:ServiceType,
    @ColumnInfo
    val ServiceCost:Long,
    @ColumnInfo
    val ServiceUnit:String? = "",
    @ColumnInfo
    val MotelID:Int


) : Parcelable