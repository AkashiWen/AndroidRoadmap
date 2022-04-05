package com.akashi.roadmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akashi.roadmap.common.clickJitter
import com.akashi.roadmap.databinding.ActivityMainBinding
import com.akashi.roadmap.glide.GlideActivity
import com.akashi.roadmap.okhttp.OkHttpActivity
import com.akashi.roadmap.proxyPattern.SubjectProxyActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOkHttp.clickJitter {
            Intent(this, OkHttpActivity::class.java).run {
                startActivity(this)
            }
        }

        binding.btnGlide.clickJitter {
            Intent(this, GlideActivity::class.java).run {
                startActivity(this)
            }
        }

        binding.btnProxy.clickJitter {
            Intent(this, SubjectProxyActivity::class.java).run {
                startActivity(this)
            }
        }

    }
}