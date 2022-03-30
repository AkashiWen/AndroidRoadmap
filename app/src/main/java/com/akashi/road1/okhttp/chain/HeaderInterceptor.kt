package com.akashi.road1.okhttp.chain

import android.util.Log
import com.akashi.road1.okhttp.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: InterceptorChain): Response {
        Log.w("interceptor", "头信息拦截器...")
        return chain.proceed()
    }
}