package com.akashi.road1.okhttp.chain

import android.util.Log
import com.akashi.road1.okhttp.Response

class ConnectionInterceptor : Interceptor {
    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "长链接拦截器...")
        return chain.proceed()
    }
}