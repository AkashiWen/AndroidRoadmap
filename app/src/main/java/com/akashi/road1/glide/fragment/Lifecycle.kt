package com.akashi.road1.glide.fragment

interface Lifecycle {

    fun addListener(lifecycleListener: LifecycleListener)

    fun removeListener(lifecycleListener: LifecycleListener)
}