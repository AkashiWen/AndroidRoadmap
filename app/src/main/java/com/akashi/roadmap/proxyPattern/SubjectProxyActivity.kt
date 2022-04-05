package com.akashi.roadmap.proxyPattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.akashi.roadmap.databinding.ActivitySubjectProxyBinding
import com.akashi.roadmap.proxyPattern.bean.WeatherBean

class SubjectProxyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectProxyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectProxyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "https://v.juhe.cn/historyWeather/citys"
        val params = mapOf<String, Any>(
            Pair("province_id", "2"),
            Pair("key", "bb52107206585ab074f5e59a8c73875b")
        )
        HttpProxy.obtain().post(url, params, object : HttpCallback<WeatherBean>() {
            override fun onSuccess(objResult: WeatherBean) {
                Log.w("SubjectProxyActivity", "onSuccess: $objResult")
                Toast.makeText(this@SubjectProxyActivity, objResult.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }
}