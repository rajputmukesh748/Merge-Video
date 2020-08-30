package com.mukesh.videomerge.utils

import android.net.Uri
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Mukesh
 * 30 Aug 2020
 * */

object BindingAdapter {

    @BindingAdapter(value = ["adapter"], requireAll = false)
    @JvmStatic
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>){
        recyclerView.adapter = adapter
    }


    @BindingAdapter(value = ["videoPath"], requireAll = false)
    @JvmStatic
    fun setVideoPath(videoView: VideoView, path: String){
        Log.e("Video_Path","$path")
        val mediaController = MediaController(videoView.context)
        videoView.setVideoURI(Uri.parse(path))
        videoView.setMediaController(mediaController)
        videoView.start()
    }

}