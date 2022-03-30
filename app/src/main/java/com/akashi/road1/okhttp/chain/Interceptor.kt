package com.akashi.road1.okhttp.chain

import com.akashi.road1.okhttp.Response
import java.io.IOException

interface Interceptor {

    @Throws(IOException::class)
    fun intercept(chain: InterceptorChain): Response
}