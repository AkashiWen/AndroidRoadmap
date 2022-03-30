package com.akashi.roadmap.glide.fragment

import androidx.fragment.app.Fragment
import com.akashi.roadmap.common.logI

/**
 * Glide - entrance
 * 用于被动监测Activity的生命周期
 * 在适当时机释放内存
 */
class EmptyFragment : Fragment() {

    private val lifecycle: ActivityFragmentLifecycle by lazy {
        ActivityFragmentLifecycle()
    }

    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
        logI("onStart")
    }

    override fun onStop() {
        super.onStop()
        lifecycle.onStop()
        logI("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.onDestroy()
        logI("onDestroy")
    }
}