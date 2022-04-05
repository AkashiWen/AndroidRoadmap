package com.akashi.roadmap.proxyPattern.realSubjects

import android.os.Handler
import android.os.Looper
import com.akashi.roadmap.proxyPattern.ICallback
import com.akashi.roadmap.proxyPattern.IHttpProcessor
import okhttp3.*
import java.io.IOException

/**
 * 源角色：RealSubject
 * 的Okhttp实现
 */
class OkhttpProcessor : IHttpProcessor {

    private val okHttpClient by lazy { OkHttpClient() }
    private val mHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun post(url: String, params: Map<String, Any>, callback: ICallback) {
        val requestBody = appendBody(params)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mHandler.post {
                    callback.onFailure()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body()?.string() ?: ""
                if (response.isSuccessful) {
                    mHandler.post {
                        callback.onSuccess(responseString)
                    }
                } else {
                    mHandler.post {
                        callback.onFailure()
                    }
                }
            }

        })


    }

    private fun appendBody(params: Map<String, Any>): RequestBody {
        val builder = FormBody.Builder()
        for (entry in params.entries) {
            builder.add(entry.key, entry.value.toString())
        }
        return builder.build()
    }
}