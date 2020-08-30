package com.mukesh.videomerge.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.ObservableField
import com.mukesh.videomerge.databinding.ProgressLoaderBinding

object ProgressAlert {

    var alertDialog: AlertDialog? = null

    fun progressAlert(context: Context) {
        try {
            val builder = AlertDialog.Builder(context)
            val layout = ProgressLoaderBinding.inflate(LayoutInflater.from(context), null, false)
            builder.setCancelable(false)
            builder.setView(layout.root)
            alertDialog = builder.create()

            alertDialog!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun dismissProgress(){
        if (alertDialog != null){
            alertDialog!!.dismiss()
        }
    }

}