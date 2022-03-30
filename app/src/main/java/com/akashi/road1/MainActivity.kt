package com.akashi.road1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akashi.road1.common.clickJitter
import com.akashi.road1.databinding.ActivityMainBinding
import com.akashi.road1.glide.Glide
import com.akashi.road1.glide.GlideActivity
import com.akashi.road1.okhttp.OkHttpActivity

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

    }
}