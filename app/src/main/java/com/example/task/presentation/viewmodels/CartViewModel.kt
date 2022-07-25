package com.example.task.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.FakeRepository
import com.example.task.data.models.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: FakeRepository
) : ViewModel() {
    private val _cartList = MutableLiveData<List<Cart>>()
    var cartList: LiveData<List<Cart>> = _cartList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _cartList.postValue(repository.DbCart().getAllCartItems())
        }
    }

    fun delete(cart: Cart){
        viewModelScope.launch(Dispatchers.IO){
            repository.DbCart().delete(cart)
            _cartList.postValue(repository.DbCart().getAllCartItems())
        }
    }

    fun totalPrice(): Double{
        return _cartList.value!!.sumOf { it.price }
    }
}