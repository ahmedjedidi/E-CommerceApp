package com.example.e_commerce_app.shared.data.repository

import java.lang.Exception

sealed class ApiResult<out T>{
    data class  Failure(val exception: Exception) : ApiResult<Nothing>()
    data class Success<T>(val data : T) : ApiResult<T>()
}
