package com.akashi.roadmap.proxyPattern

import com.akashi.roadmap.proxyPattern.reflect.analysisClassInfo
import com.google.gson.Gson

abstract class HttpCallback<T> : ICallback {

    private val gson by lazy {
        Gson()
    }

    override fun onSuccess(result: String) {
        // 1. 调用者用什么样的bean来接收数据（字节码对象）（即T具体是什么）
        val clz = analysisClassInfo(this)
        // 2. string转换成bean对象
        val objResult = gson.fromJson<T>(result, clz)
        // 3. 将这个对象回调出去
        onSuccess(objResult)
    }

    /**
     * 模板方法，回调数据
     */
    abstract fun onSuccess(objResult: T)

    override fun onFailure() {
    }
}