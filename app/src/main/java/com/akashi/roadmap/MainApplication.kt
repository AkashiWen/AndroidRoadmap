package com.akashi.roadmap

import android.app.Application
import com.akashi.roadmap.proxyPattern.HttpProxy
import com.akashi.roadmap.proxyPattern.realSubjects.OkhttpProcessor

class MainApplication : Application() {

    companion object {
        lateinit var context: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        // 1. 隔离层 —— 初始化proxyPattern下代理角色HttpHelper持有的源对象
        HttpProxy.init(OkhttpProcessor())
//        HttpHelper.instance.post()
    }
}