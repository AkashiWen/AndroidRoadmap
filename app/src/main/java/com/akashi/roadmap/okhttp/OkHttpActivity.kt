package com.akashi.roadmap.okhttp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akashi.roadmap.databinding.ActivityOkHttpBinding
import java.io.IOException

class OkHttpActivity : AppCompatActivity() {

    lateinit var binding: ActivityOkHttpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOkHttpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val okClient = OkHttpClient()
            val request = Request.Builder()
                .url("")
                .build()

            val call = okClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@OkHttpActivity, e.message, Toast.LENGTH_SHORT).show()
                        Log.e("onFailure", e.message.toString())
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        Toast.makeText(
                            this@OkHttpActivity,
                            response.body.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Log.i("onResponse", response.body.toString())
                    }
                }

            })

        }
    }
}