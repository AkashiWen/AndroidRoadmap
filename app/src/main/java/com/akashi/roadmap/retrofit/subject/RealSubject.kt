package com.akashi.roadmap.retrofit.subject

import android.widget.Toast
import com.akashi.roadmap.MainApplication
import com.akashi.roadmap.common.logI

/**
 * 被代理者
 */
class RealSubject : ISubject {
    override fun buySth() {
        Toast.makeText(MainApplication.instance, "动态代理执行完毕", Toast.LENGTH_SHORT).show()
        logI("RealSubject buySth()")
    }
}