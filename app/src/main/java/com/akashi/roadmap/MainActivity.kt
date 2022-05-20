package com.akashi.roadmap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashi.roadmap.annotation.AnnotationActivity
import com.akashi.roadmap.common.clickJitter
import com.akashi.roadmap.databinding.ActivityMainBinding
import com.akashi.roadmap.glide.GlideActivity
import com.akashi.roadmap.okhttp.OkHttpActivity
import com.akashi.roadmap.proxyPattern.SubjectProxyActivity
import com.akashi.roadmap.retrofit.RetrofitActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOkHttp.clickJitter {
            launchActivity(OkHttpActivity::class.java)
        }

        binding.btnGlide.clickJitter {
            launchActivity(GlideActivity::class.java)
        }

        binding.btnProxy.clickJitter {
            launchActivity(SubjectProxyActivity::class.java)
        }

        binding.btnApt.clickJitter {
            launchActivity(AnnotationActivity::class.java)
        }

        binding.btnDynamicProxy.clickJitter {
            launchActivity(RetrofitActivity::class.java)
        }
    }

    private fun launchActivity(activity: Class<out AppCompatActivity>) {
        Intent(this, activity).run {
            startActivity(this)
        }
    }
}