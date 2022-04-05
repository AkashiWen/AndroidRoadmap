package com.akashi.roadmap

import android.app.Application
import com.akashi.roadmap.proxyPattern.HttpProxy
import com.akashi.roadmap.proxyPattern.module.proxyPatternModule
import com.akashi.roadmap.proxyPattern.realSubjects.OkhttpProcessor
import com.akashi.roadmap.proxyPattern.realSubjects.VolleyProcessor
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object {
        lateinit var context: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        // 1. 隔离层 —— 初始化proxyPattern下代理角色HttpHelper持有的源对象
        HttpProxy.init(OkhttpProcessor())
//        HttpProxy.init(VolleyProcessor(this))

        // 2. 隔离层 —— 初始化Koin代替代理模式（不需要HttpProxy）
        startKoin {
            modules(proxyPatternModule)
        }
    }
}