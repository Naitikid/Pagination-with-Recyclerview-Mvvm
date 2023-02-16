package com.example.mypostapicallingtest.mainActivity

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mypostapicallingtest.utlis.Resource
import com.example.mypostapicallingtest.utlis.ResponseCodeCheck
import com.example.mypostapicallingtest.webservice.RetrofitClient
import com.example.mypostapicallingtest.webservicedataclass.DefaultResponse
import com.example.mypostapicallingtest.webservicedataclass.UserwebServiceApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

class Mainviewmodel(application: Application) : AndroidViewModel(application) {

    var responseCodeCheckM: ResponseCodeCheck = ResponseCodeCheck()
    private var datamutableM: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    var dataLiveDataM: MutableLiveData<Resource<DefaultResponse>> = datamutableM

    fun displayUser(page: Int) {

        Log.e(TAG, "displayUser web service number: $page ")

        val hashMap: HashMap<String, String> = HashMap()
        hashMap.apply {
            put("page", page.toString())
        }
        val webserviceinterface = RetrofitClient.getRetrofit().create(UserwebServiceApi::class.java)
        val mainRepository = MainRepository(webserviceinterface)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository: Response<DefaultResponse> = mainRepository.displayUser(hashMap)
                datamutableM.postValue(responseCodeCheckM.getResponseResult(repository))
                Log.e(TAG, "displayUser: CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC")
            } catch (e: Exception) {
                datamutableM.postValue(Resource.error("fill the details", null))
                Log.e(TAG, "displayUser: ffffffffffffffffffffffffffffff")
            }
        }
    }
}
