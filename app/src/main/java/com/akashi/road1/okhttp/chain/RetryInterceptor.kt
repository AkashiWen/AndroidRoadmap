package com.akashi.road1.okhttp.chain

import android.util.Log
import com.akashi.road1.okhttp.Response
import java.io.IOException

/**
 * 重试拦截器
 */
class RetryInterceptor : Interceptor {

    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "重试拦截器...")
        val call = chain.call
        val retries = chain.call.okHttpClient.retries

        var i = 1
        while (true) {
            try {
                if (call.isCanceled()) {
                    throw IOException()
                }
                return chain.proceed()
            } catch (e: Throwable) {
                Log.e("retry", i.toString())
                Log.e("exception", e.message.toString())
                if (i <= retries) {
                    i++
                    continue
                } else {
                    throw e
                }
            }
        }
    }
}