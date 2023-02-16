package com.example.mypostapicallingtest.utlis

import retrofit2.Response
import retrofit2.Response.error
import retrofit2.Response.success
import kotlin.Result.Companion.success

open class ResponseCodeCheck {

    open fun <T> getResponseResult(response: Response<T>): Resource<T> {
        return if (response.code() == 200) {
            Resource.success(response.body())
        } else {
            Resource.error("", response.body())
        }
    }
}