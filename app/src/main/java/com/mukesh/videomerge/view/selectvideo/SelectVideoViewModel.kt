package com.mukesh.videomerge.view.selectvideo

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import VideoHandle.OnEditorListener
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mukesh.videomerge.utils.CommonCodes
import com.mukesh.videomerge.utils.CommonMethod
import com.mukesh.videomerge.utils.Permissions
import com.mukesh.videomerge.utils.ProgressAlert
import com.mukesh.videomerge.view.adapter.SelectVideoAdapter
import com.mukesh.videomerge.view.playvideo.PlayVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class SelectVideoViewModel(var context: Context) : ViewModel() {

    var selectVideoAdapter = SelectVideoAdapter(context)
    var selectVideoData = SelectVideoData()


    /** Select video from camera */
    fun cameraSelect() {
        if (Permissions.arePermissionsGranted(context)) {
            val values = ContentValues()

            val fileUri = context.contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values
            ) as Uri

            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also {
                it.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                it.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
                (context as Activity).startActivityForResult(it, CommonCodes.REQUEST_VIDEO_CAPTURE)
            }
        } else {
            Permissions.locPermissionCheck(context as Activity)
        }
    }


    /** Select a video from gallery */
    fun gallerySelect() {
        if (Permissions.arePermissionsGranted(context)) {
            Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI).also {
                (context as Activity).startActivityForResult(it, CommonCodes.REQUEST_VIDEO_PICK)
            }
        } else {
            Permissions.locPermissionCheck(context as Activity)
        }
    }


    /** Merge Click */
    fun mergeClick() {
        try {
            if (selectVideoData.videoList.size >= 2) {
                mergeVideos()
            } else {
                Toast.makeText(context, "Please select at least two videos.", Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /** Play Merge Video */
    fun playVideo(){
        try {
            Intent(context,PlayVideo::class.java).also {
                it.putExtra("videoPath",selectVideoData.mergeVideoPath.get())
                (context as Activity).startActivity(it)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /** Handle a activity result */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        try {
            when (requestCode) {
                CommonCodes.REQUEST_VIDEO_CAPTURE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        CommonMethod.getVideoPath(data.data!!, context)?.let {
                            selectVideoData.videoList.add(it)
                            selectVideoData.listSize.set(selectVideoData.videoList.size.toString())
                            selectVideoAdapter.submitList(selectVideoData.videoList)
                            selectVideoAdapter.notifyDataSetChanged()
                        }
                    }
                }

                CommonCodes.REQUEST_VIDEO_PICK -> {
                    if (resultCode == Activity.RESULT_OK) {
                        CommonMethod.getVideoPath(data.data!!, context)?.let {
                            selectVideoData.videoList.add(it)
                            selectVideoData.listSize.set(selectVideoData.videoList.size.toString())
                            selectVideoAdapter.submitList(selectVideoData.videoList)
                            selectVideoAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




    /** Merge Video Method */
    private fun mergeVideos() {

        //Show Progress Alert
        ProgressAlert.progressAlert(context)

        val dir = context.filesDir.absolutePath + "/MergeVideo/"
        val outputFile = File(dir + System.currentTimeMillis() + "merged_video.mp4")
        try {
            if (!outputFile.exists()) {
                File(dir).mkdirs()     //Create a new Directory
                outputFile.createNewFile()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val epVideos = ArrayList<EpVideo>()
        for (item in selectVideoData.videoList) {
            epVideos.add(EpVideo(item))
        }
        val outputOption = EpEditor.OutputOption(outputFile.path)
        outputOption.setWidth(720)
        outputOption.setHeight(1280)
        outputOption.frameRate = 30

        EpEditor.merge(epVideos, outputOption, object : OnEditorListener {
            override fun onSuccess() {
                Log.e("MurgeVideoPath","${outputFile.path}")
                selectVideoData.mergeVideoPath.set(outputFile.path)
                ProgressAlert.dismissProgress()
            }

            override fun onFailure() {
                try {
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "Something went wrong. Please try again", Toast.LENGTH_LONG)
                            .show()
                        ProgressAlert.dismissProgress()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onProgress(progress: Float) {
                Log.e("ProgressValue","$progress")
            }

        })
    }


    /** Data Class for store a data  */
    data class SelectVideoData(
        var videoList: ArrayList<String> = ArrayList(),
        var listSize: ObservableField<String> = ObservableField(""),
        var mergeVideoPath: ObservableField<String> = ObservableField("")
    )

}