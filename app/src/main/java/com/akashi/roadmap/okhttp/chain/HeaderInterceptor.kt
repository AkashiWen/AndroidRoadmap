package com.akashi.roadmap.okhttp.chain

import android.util.Log
import com.akashi.roadmap.okhttp.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "头信息拦截器...")
        return chain.proceed()
    }
}