package com.akashi.road1.okhttp.chain

import com.akashi.road1.okhttp.Call
import com.akashi.road1.okhttp.Response
import java.io.IOException

/**
 * 责任链管理者
 */
class InterceptorChain(
    private val interceptors: List<Interceptor>,
    private val index: Int,
    val call: Call
) {

    /**
     * do real request
     */
    @Throws(IOException::class)
    fun proceed(): Response {
        if (index > interceptors.size - 1) {
            return call.response
        }

        check(index < interceptors.size) { "interceptor out of index" }

        val next = InterceptorChain(interceptors, index + 1, call)

        return interceptors[index].intercept(next)
    }

}