package com.mukesh.videomerge.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.videomerge.databinding.ImagesListBinding
import java.lang.Exception

class SelectVideoAdapter(var context: Context) : ListAdapter<String,SelectVideoAdapter.ViewHolder>(SelectVideoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImagesListBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val dataClass = getItem(position)
            holder.setData(context,dataClass)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    class ViewHolder(private val binding : ImagesListBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(context: Context, dataClass: String) {
            binding.videoPath = dataClass
            binding.executePendingBindings()
        }
    }

}