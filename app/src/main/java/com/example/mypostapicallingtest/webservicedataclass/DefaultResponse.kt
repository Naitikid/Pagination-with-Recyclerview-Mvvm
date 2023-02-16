package com.example.mypostapicallingtest.webservicedataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DefaultResponse(
    @SerializedName("data")
    var`data`:List<UserData>,
    @SerializedName("status")
    var status: Boolean?,
    @SerializedName("message")
    var message: String?

) : Serializable

data class UserData (
    @SerializedName("id")
    var id:Int?,
    @SerializedName("name")
    var name:String?,
    @SerializedName("email")
    var email:String?,
    @SerializedName("profile_picture")
    var profilePicture :String?,
    ): Serializable