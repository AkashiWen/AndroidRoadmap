package com.akashi.road1.glide.cache

import android.util.LruCache
import com.akashi.road1.glide.data.EngineResource

/**
 * 2. 内存缓存
 * Lru: least recently used
 */
class MemoryCache(maxSize: Int): LruCache<String, EngineResource>(maxSize) {

    /**
     * 手动删除
     */
    fun shouldRemove(key: String): EngineResource? {
        return remove(key)
    }

    override fun entryRemoved(
        evicted: Boolean,
        key: String?,
        oldValue: EngineResource?,
        newValue: EngineResource?
    ) {
        super.entryRemoved(evicted, key, oldValue, newValue)
    }

    override fun sizeOf(key: String?, engineResource: EngineResource): Int {
        val bitmap = engineResource.mBitmap
        return bitmap?.byteCount ?: 0
    }
}