package com.akashi.roadmap.glide.fragment

interface Lifecycle {

    fun addListener(lifecycleListener: LifecycleListener)

    fun removeListener(lifecycleListener: LifecycleListener)
}