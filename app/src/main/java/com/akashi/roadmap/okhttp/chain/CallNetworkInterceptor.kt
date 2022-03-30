package com.akashi.roadmap.okhttp.chain

import android.util.Log
import com.akashi.roadmap.okhttp.Response

class CallNetworkInterceptor : Interceptor {
    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "执行call拦截器...")
        chain.call.response = Response.Builder().buildFakeDataResponse()
        return chain.proceed()
    }
}