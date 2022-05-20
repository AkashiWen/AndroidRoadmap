package com.akashi.roadmap

import android.app.Application
import android.os.Build
import com.akashi.roadmap.proxyPattern.HttpProxy
import com.akashi.roadmap.proxyPattern.module.proxyPatternModule
import com.akashi.roadmap.proxyPattern.realSubjects.OkhttpProcessor
import com.akashi.roadmap.utils.startFPSMonitor
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 1. 隔离层 —— 初始化proxyPattern下代理角色HttpHelper持有的源对象
        HttpProxy.init(OkhttpProcessor())
//        HttpProxy.init(VolleyProcessor(this))

        // 2. 隔离层 —— 初始化Koin代替代理模式（不需要HttpProxy）
        startKoin {
            modules(proxyPatternModule)
        }

        // 3. 帧率检测
        if (BuildConfig.DEBUG) startFPSMonitor()
    }
}