package com.example.task.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.FakeRepository
import com.example.task.data.models.Response
import com.example.task.utilities.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: FakeRepository) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    lateinit var categories: Response<List<String>>

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.Default) {
            categories = (repository.API().getCategories())
            _status.postValue(categories.status)
            Log.d("Debug Coroutine", "I am ${Thread.currentThread().name} and my ID is ${Thread.currentThread().name}")
        }
    }
}