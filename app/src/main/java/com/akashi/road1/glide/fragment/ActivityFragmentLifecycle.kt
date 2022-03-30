package com.akashi.road1.glide.fragment

/**
 * 管理所有生命周期感知者
 */
class ActivityFragmentLifecycle : Lifecycle {

    private val lifecycleListeners: MutableSet<LifecycleListener> = mutableSetOf()

    override fun addListener(lifecycleListener: LifecycleListener) {
        lifecycleListeners.add(lifecycleListener)
    }

    override fun removeListener(lifecycleListener: LifecycleListener) {
        lifecycleListeners.remove(lifecycleListener)
    }

    fun onStart() {
        for (listener in lifecycleListeners) {
            listener.onStart()
        }
    }

    fun onStop() {
        for (listener in lifecycleListeners) {
            listener.onStop()
        }
    }

    fun onDestroy() {
        for (listener in lifecycleListeners) {
            listener.onDestroy()
        }
    }

}

interface LifecycleListener {
    fun onStart()
    fun onStop()
    fun onDestroy()
}