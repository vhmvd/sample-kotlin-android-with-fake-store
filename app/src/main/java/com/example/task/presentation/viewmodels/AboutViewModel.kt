package com.example.task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.task.data.models.Product
import com.example.task.data.models.Rating

class AboutViewModel: ViewModel() {
    var currentProduct = Product(1, "1", 1.0, "1", "1", "1", Rating(1F, 1))

    fun update(product: Product) {
            currentProduct = product
    }
}