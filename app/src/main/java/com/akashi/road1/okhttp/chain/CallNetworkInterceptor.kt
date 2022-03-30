package com.akashi.road1.okhttp.chain

import android.util.Log
import com.akashi.road1.okhttp.Response

class CallNetworkInterceptor : Interceptor {
    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "执行call拦截器...")
        chain.call.response = Response.Builder().buildFakeDataResponse()
        return chain.proceed()
    }
}