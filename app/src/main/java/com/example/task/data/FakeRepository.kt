package com.example.task.data

import com.example.task.data.local.AppDatabase
import com.example.task.data.local.api.FakeApiService
import com.example.task.data.models.Cart
import com.example.task.data.models.Product
import com.example.task.data.models.Response
import com.example.task.utilities.ApiStatus
import javax.inject.Inject

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FakeRepository @Inject constructor(
    private val service: FakeApiService,
    private val db: AppDatabase
) {

    inner class DbCart {
        suspend fun getCartCount() = db.CartDao().getCount()
        suspend fun getAllCartItems() = db.CartDao().getAll()
        suspend fun insert(cart: Cart) = db.CartDao().insert(cart)
        suspend fun delete(cart: Cart) = db.CartDao().delete(cart)
    }

    inner class API {
        suspend fun getProducts(category: String): Response<MutableList<Product>> {
            return try {
                Response(
                    service.getProducts(category)
                        .also { DbProduct().insertAll(it as List<Product>) }, ApiStatus.DONE
                )
            } catch (e: Exception) {
                val data = db.ProductDao().getProducts(category) as MutableList<Product>
                when (data.isNotEmpty()) {
                    true -> Response(data, ApiStatus.LOCAL)
                    else -> Response(ArrayList(), ApiStatus.ERROR)
                }
            }
        }

        suspend fun getCategories(): Response<List<String>> {
            return try {
                Response(service.getCategories(), ApiStatus.DONE)
            } catch (e: Exception) {
                val data = db.ProductDao().getCategories()
                when (data.isNotEmpty()) {
                    true -> Response(data, ApiStatus.LOCAL)
                    else -> Response(data, ApiStatus.ERROR)
                }
            }
        }
    }

    inner class DbProduct {
        suspend fun insertAll(products: List<Product>) = db.ProductDao().insertAll(products)
        suspend fun getAll(category: String): List<Product> = db.ProductDao().getProducts(category)
    }
}