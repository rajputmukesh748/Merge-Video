package com.mukesh.videomerge.view.playvideo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mukesh.videomerge.R
import com.mukesh.videomerge.databinding.PlayVideoBinding

class PlayVideo : AppCompatActivity() {

    private lateinit var playVideoBinding: PlayVideoBinding
    private lateinit var playVideoFactory: PlayVideoFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel
    private var videoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playVideoBinding = DataBindingUtil.setContentView(this, R.layout.play_video)
        getData()
    }


    private fun getData() {
        try {
            if (!intent.getStringExtra("videoPath").isNullOrEmpty()) {
                videoPath = intent.getStringExtra("videoPath")!!
            }
            Log.e("VideoPath","mkmskmxks     $videoPath")
            playVideoFactory = PlayVideoFactory(this, videoPath)
            playVideoViewModel =
                ViewModelProvider(this, playVideoFactory).get(PlayVideoViewModel::class.java)
            playVideoBinding.playVideoVM = playVideoViewModel
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}