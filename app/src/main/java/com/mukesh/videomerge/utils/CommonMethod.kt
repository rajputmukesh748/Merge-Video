package com.mukesh.videomerge.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

object CommonMethod {


    /** Get Video Path from uri */
    fun getVideoPath(uri: Uri, context: Context): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor =
            (context as Activity).contentResolver.query(uri, projection, null, null, null)!!
        return run {
            val index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(index)
        }
    }

}