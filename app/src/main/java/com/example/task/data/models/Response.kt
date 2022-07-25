package com.example.task.data.models

import com.example.task.utilities.ApiStatus

data class Response<T>(val data: T, val status: ApiStatus)