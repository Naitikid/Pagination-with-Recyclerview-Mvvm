package com.example.mypostapicallingtest.mainActivity

import com.example.mypostapicallingtest.webservicedataclass.DefaultResponse
import com.example.mypostapicallingtest.webservicedataclass.UserwebServiceApi
import retrofit2.Response

class MainRepository constructor(private val userwebServiceApi: UserwebServiceApi) {
    suspend fun displayUser(hashMap: HashMap<String,String>): Response<DefaultResponse>
    {
        return userwebServiceApi.displayUser(hashMap)
    }
}