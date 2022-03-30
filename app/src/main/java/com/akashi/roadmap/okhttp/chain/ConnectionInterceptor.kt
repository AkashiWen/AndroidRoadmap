package com.akashi.roadmap.okhttp.chain

import android.util.Log
import com.akashi.roadmap.okhttp.Response

class ConnectionInterceptor : Interceptor {
    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "长链接拦截器...")
        return chain.proceed()
    }
}