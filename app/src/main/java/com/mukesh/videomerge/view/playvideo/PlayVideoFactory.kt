package com.mukesh.videomerge.view.playvideo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mukesh.videomerge.R

class PlayVideoFactory(var context: Context, var videoPath: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayVideoViewModel::class.java)){
            return PlayVideoViewModel(context,videoPath) as T
        }
        throw IllegalAccessException(context.getString(R.string.throw_exception))
    }
}