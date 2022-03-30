package com.akashi.road1.glide.cache

import com.akashi.road1.common.logE
import com.akashi.road1.glide.data.EngineResource
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/**
 * 1. 活动缓存
 * WeakReference
 */
class ActiveCache {

    /**
     * WeakReference弱引用能减少内存浪费
     * 但也会造成map中某些key取不到数据
     * 所以需要使用ReferenceQueue监听内存回收
     */
    private val map: MutableMap<String, WeakReference<EngineResource>> = mutableMapOf()

    private val queue: ReferenceQueue<EngineResource> by lazy {
        ReferenceQueue<EngineResource>()
    }

    private var isThreadClosed = false
    private val thread: Thread by lazy {
        Thread {
            // 阻塞式线程
            while (!isThreadClosed) {
                try {
                    // 回收bitmap
                    val remove = queue.remove()
                    remove.get()?.let {
                        it.mBitmap?.recycle()
                    }
                    // 从cache map移除
                    map.remove(remove.get()?.key)
                } catch (e: InterruptedException) {
                    logE(e.message.toString())
                }
            }
        }
    }

    fun get(key: String): EngineResource? {
        map[key]?.let {
            return it.get()
        }
        return null
    }

    fun put(key: String, engineResource: EngineResource) {
        map[key] = WeakReference(engineResource, getQueue())
    }

    /**
     * 当弱引用被回收，首先被放入回收queue，由我们自行处理
     */
    @JvmName("getQueue1")
    private fun getQueue(): ReferenceQueue<EngineResource> {
        thread
        return queue
    }

}