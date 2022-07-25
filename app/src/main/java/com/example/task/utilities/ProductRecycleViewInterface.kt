package com.example.task.utilities

import com.example.task.data.models.Product

interface ProductRecycleViewInterface {
    fun onItemClick(product: Product)
}