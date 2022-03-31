package com.akashi.roadmap.common

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.math.BigInteger
import java.security.MessageDigest


fun log(msg: String, tag: String = "debug") {
    Log.d(tag, msg)
}

fun logI(msg: String, tag: String = "info") {
    Log.i(tag, msg)
}

fun logE(msg: String, tag: String = "error") {
    Log.e(tag, msg)
}

fun logW(msg: String, tag: String = "warn") {
    Log.w(tag, msg)
}

inline fun View.clickJitter(crossinline block: () -> Unit) {
    this.setOnClickListener {
        this.isClickable = false
        block.invoke()
        this.postDelayed({
            this.isClickable = true
        }, 1000L);
    }
}

fun Activity.checkPermissionReadStorage(requestCode: Int) {
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                )
            } else {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                )
            }.let {
                ActivityCompat.requestPermissions(
                    this,
                    it,
                    requestCode
                )
            }
        }
    }
}


fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}