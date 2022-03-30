package com.akashi.roadmap.glide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akashi.roadmap.common.checkPermissionReadStorage
import com.akashi.roadmap.common.clickJitter
import com.akashi.roadmap.common.logI
import com.akashi.roadmap.databinding.ActivityGlideBinding

class GlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionReadStorage(1)

        binding.btn1.clickJitter {
            Glide.with(this)
                .load("https://th.bing.com/th/id/OIP.6z9hYR_oUjjZnN3y1tQ-AwHaQq?pid=ImgDet&rs=1")
                .into(binding.image1)
        }

        binding.btn2.clickJitter {
            Glide.with(this)
                .load("https://th.bing.com/th/id/OIP.6z9hYR_oUjjZnN3y1tQ-AwHaQq?pid=ImgDet&rs=1")
                .into(binding.image2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        logI("requestCode: $requestCode -- granted permission size:${grantResults.size}")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}