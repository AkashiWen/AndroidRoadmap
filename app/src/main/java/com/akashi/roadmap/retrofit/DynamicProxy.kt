package com.akashi.roadmap.retrofit

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class DynamicProxy(private val obj: Any) : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        //
        method?.invoke(obj, *(args ?: emptyArray()))
        return obj
    }
}