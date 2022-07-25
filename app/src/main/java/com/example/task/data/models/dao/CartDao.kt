package com.example.task.data.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.task.data.models.Cart

@Dao
interface CartDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(cart: Cart)
    @Delete
    suspend fun delete(cart: Cart)
    @Query("SELECT * FROM cart_table")
    suspend fun getAll(): List<Cart>
    @Query("SELECT COUNT(*) FROM cart_table")
    suspend fun getCount(): Int
}