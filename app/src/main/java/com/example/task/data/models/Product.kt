package com.example.task.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "price") val price:Double,
    @ColumnInfo(name = "category") val category:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "image_url") val image:String,
    @Embedded
    val rating: Rating
) : Parcelable
