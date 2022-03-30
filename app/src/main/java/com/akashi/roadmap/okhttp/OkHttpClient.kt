package com.akashi.roadmap.okhttp

class OkHttpClient {

    var retries: Int = 2

    val dispatcher = Dispatcher()


    /**
     * create a new call
     */
    fun newCall(request: Request): Call {
        return Call(this, request)
    }

    /**
     * 设置重试次数
     */
    fun setRetries(retries: Int): OkHttpClient {
        this.retries = retries
        return this
    }
}