package com.example.mypostapicallingtest.webservicedataclass

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface UserwebServiceApi{
    @FormUrlEncoded
    @POST("users.php")
    suspend fun displayUser(@FieldMap hashMap: HashMap<String, String>):Response<DefaultResponse>

   // @FormUrlEncoded
   // @POST("users.php?page")
   // suspend fun displayUser1 (@FieldMap hashMap: HashMap<String, String>):Response<DefaultResponse>
}

