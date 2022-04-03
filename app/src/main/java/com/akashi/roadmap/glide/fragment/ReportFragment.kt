package com.akashi.roadmap.glide.fragment

import androidx.fragment.app.Fragment
import com.akashi.roadmap.common.logI

/**
 * Glide - entrance
 * 用于被动监测Activity的生命周期
 * 在适当时机释放内存
 */
class ReportFragment(private val lifecycle: ActivityFragmentLifecycle) : Fragment() {

    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
    }

    override fun onStop() {
        super.onStop()
        lifecycle.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.onDestroy()
    }
}