package com.akashi.road1.okhttp

import com.akashi.road1.okhttp.chain.*
import java.io.IOException

class Call(val okHttpClient: OkHttpClient, val request: Request) {

    private var isCanceled = false

    fun isCanceled() = isCanceled

    lateinit var response: Response

    /**
     * 分发请求
     */
    fun enqueue(callback: Callback) {
        synchronized(this) {
            // dispatcher to request queue
            okHttpClient.dispatcher.enqueue(AsyncCall(callback))
        }
    }

    /**
     * 异步执行单元
     * 构造函数接收callback接口，用于返回线程中的Response结果
     */
    inner class AsyncCall(private val callback: Callback) : Runnable {

        override fun run() {
            try {
                val response = getResponse()
                callback.onResponse(this@Call, response)
            } catch (e: IOException) {
                callback.onFailure(this@Call, e)
            }
        }

        @Throws(IOException::class)
        private fun getResponse() =
            // 责任链
            listOf(
                // 重试
                RetryInterceptor(),
                // 公共请求头
                HeaderInterceptor(),
                // 长链接
                ConnectionInterceptor(),
                //
                CallNetworkInterceptor()
            ).let {
                InterceptorChain(it, 0, this@Call)
            }.proceed()
    }
}

interface Callback {
    fun onFailure(call: Call, e: IOException)
    fun onResponse(call: Call, response: Response)
}