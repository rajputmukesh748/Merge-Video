package com.mukesh.videomerge.view.selectvideo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mukesh.videomerge.R
import com.mukesh.videomerge.databinding.SelectVideoBinding
import com.mukesh.videomerge.utils.Permissions

class SelectVideo : AppCompatActivity() {

    private lateinit var selectVideoBinding: SelectVideoBinding
    private lateinit var selectVideoFactory: SelectVideoFactory
    private lateinit var selectVideoViewModel: SelectVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectVideoBinding = DataBindingUtil.setContentView(this, R.layout.select_video)
        selectVideoFactory = SelectVideoFactory(this)
        selectVideoViewModel =
            ViewModelProvider(this, selectVideoFactory).get(SelectVideoViewModel::class.java)
        selectVideoBinding.selectVideoVM = selectVideoViewModel
    }


    /** Handle Activity Result */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            selectVideoViewModel.onActivityResult(requestCode,resultCode,data)
        }
    }



    /** Check Permissions */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Permissions.PERMISSION_REQUEST->{
                var count = 0
                for( i in permissions.indices){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        count++
                    }
                }
                if (count != permissions.size){
                    finishAffinity()
                    Toast.makeText(this,"Permissions are required.",Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Permissions.locPermissionCheck(this)
    }


}