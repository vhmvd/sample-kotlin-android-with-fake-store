package com.example.task.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.task.data.models.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insertAll(product: List<Product>)
    @Query("SELECT * FROM product_table WHERE category = :category")
    suspend fun getProducts(category: String): List<Product>
    @Query("SELECT DISTINCT category FROM product_table ")
    suspend fun getCategories(): List<String>
}