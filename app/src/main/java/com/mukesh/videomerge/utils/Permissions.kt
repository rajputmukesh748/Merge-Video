package com.mukesh.videomerge.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

object Permissions {


    var PERMISSION_REQUEST: Int = 100

    fun arePermissionsGranted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {

            val hasWritePermission =
                context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val hasCameraPermission =
                context.checkSelfPermission(Manifest.permission.CAMERA)
            val hasReadPermission =
                context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)

            if (hasCameraPermission == PackageManager.PERMISSION_GRANTED
                && hasWritePermission == PackageManager.PERMISSION_GRANTED
                && hasReadPermission == PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
            return false
        }
        return true
    }

    /** ------  CHECK PERMISSIONS   ------- */
    fun locPermissionCheck(activity: Activity): Boolean {

        if (Build.VERSION.SDK_INT >= 23) {
            val hasReadPermission =
                activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val hasWritePermission =
                activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            val hasNetworkStatePermission = activity.checkSelfPermission(Manifest.permission.CAMERA)

            val permissionList = ArrayList<String>()

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (hasNetworkStatePermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA)
            }

            if (permissionList.isNotEmpty()) {
                activity.requestPermissions(permissionList.toTypedArray(), PERMISSION_REQUEST)
            } else {
                return true
            }
        }
        return true
    }


}