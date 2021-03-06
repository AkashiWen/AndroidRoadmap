package com.akashi.roadmap.glide.request

import androidx.fragment.app.FragmentActivity
import com.akashi.roadmap.common.logI
import com.akashi.roadmap.glide.fragment.ActivityFragmentLifecycle
import com.akashi.roadmap.glide.fragment.ReportFragment
import com.akashi.roadmap.glide.fragment.LifecycleListener

/**
 * Glide.with(context) 之后最先初始化的模块
 * 负责
 * 1. 创建Fragment感知生命周期
 * 2. 注入图片地址
 */
class RequestManager(private val fragmentActivity: FragmentActivity) : LifecycleListener {

    companion object {
        const val FRAGMENT_TAG = "Empty_FragmentActivity"
    }

    private val contextLifecycle: ActivityFragmentLifecycle by lazy {
        ActivityFragmentLifecycle()
    }

    private val engine: Engine by lazy { Engine() }

    init {
        fragmentActivity.supportFragmentManager.run {
            val fragment = this.findFragmentByTag(FRAGMENT_TAG)
            if (fragment == null) {
                beginTransaction().run {
                    add(ReportFragment(contextLifecycle), FRAGMENT_TAG)
                    commitAllowingStateLoss()
                }
            }
        }

        contextLifecycle.addListener(this)
    }

    fun load(path: String): Engine {
        return engine.also {
            it.load(path, fragmentActivity)
        }
    }

    /**
     * lifecycle call back
     */
    override fun onStart() {
        logI("RequestManager - onStart")
    }

    /**
     * lifecycle call back
     */
    override fun onStop() {
//        pauseRequest()
        logI("RequestManager - onStop")
    }

    /**
     * lifecycle call back
     */
    override fun onDestroy() {
        logI("RequestManager - onDestroy")
        contextLifecycle.removeListener(this)
        // 1. 关闭活动缓存
        engine.close()
        // 2. 关闭请求中的任务

        // .. 其他关闭操作
    }


}