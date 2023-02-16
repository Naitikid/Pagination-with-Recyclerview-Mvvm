package com.example.mypostapicallingtest.dataclass

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypostapicallingtest.R
import com.example.mypostapicallingtest.databinding.ItemLoadingBinding
import com.example.mypostapicallingtest.databinding.UserlistBinding
import com.example.mypostapicallingtest.webservicedataclass.UserData


class CustomAdapter(private val context: Context,
    private val mdata: MutableList<UserData>?,
    val callbacks: (UserData,Int, View) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val userlistbinding =
                UserlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserlistViewHolder(userlistbinding)
        } else {
            val iteamloading =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemLoadView(iteamloading)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val usermodelforlistdata = mdata!![position]
        if (holder is UserlistViewHolder) {
            holder.binding.username.text = usermodelforlistdata.name
            holder.binding.useremail.text = usermodelforlistdata.email
            Glide.with(context)
                .load(usermodelforlistdata.profilePicture)
                .placeholder(R.drawable.ic_launcher_background).into(holder.binding.imagevie)
            holder.itemView.setOnClickListener {
                callbacks.invoke(mdata[position], position, holder.binding.imagevie)
            }

        } else if (holder is ItemLoadView) {
            Log.d("data_information", "Show loader")
        }
    }
    override fun getItemCount(): Int {
        return mdata!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mdata!![position].id == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    inner class UserlistViewHolder(val binding: UserlistBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemLoadView(private val itemLoadingBinding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(itemLoadingBinding.root)
}
