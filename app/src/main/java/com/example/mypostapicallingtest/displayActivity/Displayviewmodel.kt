package com.example.mypostapicallingtest.displayActivity

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mypostapicallingtest.mainActivity.MainRepository
import com.example.mypostapicallingtest.utlis.Resource
import com.example.mypostapicallingtest.utlis.ResponseCodeCheck
import com.example.mypostapicallingtest.webservice.RetrofitClient
import com.example.mypostapicallingtest.webservicedataclass.DefaultResponse
import com.example.mypostapicallingtest.webservicedataclass.UserwebServiceApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class Displayviewmodel(application: Application) : AndroidViewModel(application) {

    var displaynamexml: String = ""


    var responseCodeCheckD: ResponseCodeCheck = ResponseCodeCheck()
    private var datamutableD: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    var dataLiveDataD: MutableLiveData<Resource<DefaultResponse>> = datamutableD

    fun displayUserD() {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.apply {
            put("param", "1")
        }

        val webserviceinterface = RetrofitClient.getRetrofit().create(UserwebServiceApi::class.java)
        val displayRepository = DisplayRepository(webserviceinterface)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository: Response<DefaultResponse> = displayRepository.displayUser(hashMap)
                datamutableD.postValue(responseCodeCheckD.getResponseResult(repository))
                Log.e(
                    ContentValues.TAG,
                    "displayUser: CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",
                )
            } catch (e: Exception) {
                datamutableD.postValue(Resource.error("fill the details", null))
                Log.e(ContentValues.TAG, "displayUser: ffffffffffffffffffffffffffffff")
            }
        }
    }
}

