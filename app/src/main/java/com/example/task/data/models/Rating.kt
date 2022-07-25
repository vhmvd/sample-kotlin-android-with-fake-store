package com.example.task.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @ColumnInfo(name = "rating") val rate: Float,
    @ColumnInfo(name = "order_count") val count: Int
) : Parcelable