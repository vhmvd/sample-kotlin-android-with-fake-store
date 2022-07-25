package com.example.task.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.FakeRepository
import com.example.task.data.models.Cart
import com.example.task.data.models.Product
import com.example.task.data.models.Response
import com.example.task.utilities.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: FakeRepository
) : ViewModel() {
    private var availableProductName = ""
    private val _productCount = MutableLiveData<Int>()
    val productCount: LiveData<Int> = _productCount
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>> = _products
    private lateinit var response: Response<MutableList<Product>>

    fun getProducts(category: String) {
        if (availableProductName == category) {
            return
        } else {
            availableProductName = category
        }

        viewModelScope.launch(Dispatchers.Default) {
            response = repository.API().getProducts(category)
            _status.postValue(response.status)
            updateCartCount()
            _products.postValue(response.data!!)
        }
    }

    fun sortAlphabetically() =
        _products.postValue(_products.value!!.sortedBy { it.title } as MutableList<Product>?)

    fun sortPrice() =
        _products.postValue(_products.value!!.sortedBy { it.price } as MutableList<Product>?)

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.DbCart()
                .insert(Cart(product.id, product.rating.rate, product.image, product.title, product.price))
            _productCount.postValue(repository.DbCart().getCartCount())
        }
    }

    fun updateCartCount() {
        viewModelScope.launch(Dispatchers.IO) {
            _productCount.postValue(repository.DbCart().getCartCount())
        }
    }
}
