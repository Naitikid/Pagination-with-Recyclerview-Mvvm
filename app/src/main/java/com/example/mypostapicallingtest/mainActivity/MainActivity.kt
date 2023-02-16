package com.example.mypostapicallingtest.mainActivity

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypostapicallingtest.R
import com.example.mypostapicallingtest.databinding.ActivityMainBinding
import com.example.mypostapicallingtest.dataclass.CustomAdapter
import com.example.mypostapicallingtest.displayActivity.DisplayActivity
import com.example.mypostapicallingtest.utlis.EndlessRecyclerViewScrollListener
import com.example.mypostapicallingtest.utlis.Status
import com.example.mypostapicallingtest.webservicedataclass.UserData


class MainActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainviewmodel: Mainviewmodel
    private lateinit var mdata: MutableList<UserData>
    private var customAdapter: CustomAdapter? = null

    var pagenumber: Int = 1
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mdata = mutableListOf()
        progressDialog = ProgressDialog(this)

        mainviewmodel = ViewModelProvider(this).get(Mainviewmodel::class.java)

        mainviewmodel.dataLiveDataM.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (customAdapter == null) {
                        mdata.clear()
                        mdata.addAll(it.data!!.data)
                        customAdapter = CustomAdapter(this, mdata) { UserData, Postion, View ->

                            Log.e(TAG, "onCreate: " + Postion)
                            val i = Intent(this, DisplayActivity::class.java)
                            i.putExtra("MyData", UserData)
                            startActivity(i)
                        }
                        binding.userrecyclerview.adapter = customAdapter
                        Log.e("TAG-for ID for Main", "onCreate: " + it.data?.data?.toString())
                    } else {
                        mdata.removeAt(mdata.size - 1)
                        Log.e(TAG, "onCreate mdatalist :$mdata ")
                        customAdapter!!.notifyItemRemoved(mdata.size - 1)
                        val lastpostion: Int = mdata.size
                        mdata.addAll(it.data!!.data)
                        customAdapter!!.notifyItemMoved(lastpostion, mdata.size)
                    }
                    isLoading = it.data.data?.size == 0
                    progressDialog.dismiss()
                }

                Status.LOADING -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    if (customAdapter == null) {
                        progressDialog.setMessage(resources.getString(R.string.loading))
                        progressDialog.setCancelable(false)
                        progressDialog.show()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.userrecyclerview.layoutManager = layoutManager

        binding.userrecyclerview.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (!isLoading) {
                    isLoading = true
                    pagenumber++
                    mdata.add(mdata.size, UserData(null, null, null, null))
                    customAdapter?.notifyItemInserted(mdata.size - 1)
                    val handler = Handler(Looper.myLooper()!!)
                    handler.postDelayed({
                        mainviewmodel.displayUser(page = pagenumber)
                    }, 2000)
                }
            }

        })
        mainviewmodel.displayUser(pagenumber)

    }

}
