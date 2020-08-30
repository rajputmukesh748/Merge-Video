package com.mukesh.videomerge.view.selectvideo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mukesh.videomerge.R

class SelectVideoFactory (var context: Context) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectVideoViewModel::class.java)){
            return SelectVideoViewModel(context) as T
        }
        throw IllegalAccessException(context.getString(R.string.throw_exception))
    }
}