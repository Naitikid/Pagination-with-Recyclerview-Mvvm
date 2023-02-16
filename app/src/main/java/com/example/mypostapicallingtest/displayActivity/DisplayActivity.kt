package com.example.mypostapicallingtest.displayActivity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mypostapicallingtest.R
import com.example.mypostapicallingtest.databinding.ActivityDisplayBinding
import com.example.mypostapicallingtest.utlis.Status
import com.example.mypostapicallingtest.webservicedataclass.UserData

class DisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayBinding
    private lateinit var displayviewmodel: Displayviewmodel
    private lateinit var adddata: UserData
    var pagenumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_display)
        displayviewmodel = ViewModelProvider(this).get(Displayviewmodel::class.java)
        binding.displayviewmodelxml = displayviewmodel
        if (intent != null) {
            adddata = intent.extras?.getSerializable("MyData") as UserData
            binding.displayname.text = adddata.name
            binding.displayemail.text = adddata.email
            Glide.with(this)
                .load(adddata.profilePicture).placeholder(R.drawable.ic_launcher_background)
                .into(binding.displayimage)
        }
        displayviewmodel.dataLiveDataD.observe(this)

        {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data!!.data

                    Log.e(TAG, "onCreate: $it.status.name")
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        }
        displayviewmodel.displayUserD()
    }
}