package com.akashi.roadmap.okhttp.chain

import com.akashi.roadmap.okhttp.Response
import java.io.IOException

interface Interceptor {

    @Throws(IOException::class)
    fun intercept(chain: InterceptorChain): Response
}