package com.example.task.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.task.data.models.Cart
import com.example.task.data.models.Product
import com.example.task.data.models.dao.CartDao
import com.example.task.data.models.dao.ProductDao

@Database(entities = [Cart::class, Product::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun CartDao(): CartDao
    abstract fun ProductDao(): ProductDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDB(context: Context): AppDatabase {
            val MIGRATION_1_2 = object : Migration(1, 2){
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE 'cart_table' ADD COLUMN 'rating' float not null default 0.0")
                }
            }
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_db"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}