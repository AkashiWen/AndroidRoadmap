package com.akashi.roadmap.proxyPattern

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akashi.roadmap.common.clickJitter
import com.akashi.roadmap.databinding.ActivitySubjectProxyBinding
import com.akashi.roadmap.proxyPattern.bean.WeatherBean
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class SubjectProxyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectProxyBinding

    private val httpProcessor: IHttpProcessor by inject()
    private val httpProcessor2: IHttpProcessor by inject(qualifier = named("volley"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectProxyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProxy.clickJitter {
            doPost(HttpProxy.obtain())
        }

        binding.btnKoinOkhttp.clickJitter {
            doPost(httpProcessor)
        }

        binding.btnKoinVolley.clickJitter {
            doPost(httpProcessor2)
        }
    }

    private fun doPost(httpProcessor: IHttpProcessor) {
        val url = "https://v.juhe.cn/historyWeather/citys"
        val params = mapOf<String, Any>(
            Pair("province_id", "2"),
            Pair("key", "bb52107206585ab074f5e59a8c73875b")
        )

        httpProcessor.post(url, params, object : HttpCallback<WeatherBean>() {
            override fun onSuccess(objResult: WeatherBean) {
                Log.w("SubjectProxyActivity", "onSuccess: $objResult")
                Toast.makeText(this@SubjectProxyActivity, objResult.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}