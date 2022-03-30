package com.akashi.road1.glide.request

import android.content.Context
import android.widget.ImageView
import com.akashi.road1.common.logE
import com.akashi.road1.glide.cache.ActiveCache
import com.akashi.road1.glide.cache.MemoryCache
import com.akashi.road1.glide.data.DataLoader
import com.akashi.road1.glide.data.EngineResource
import com.akashi.road1.glide.data.ResponseListener
import com.akashi.road1.glide.disk.DiskLruCacheImpl

/**
 * bitmap数据加载引擎
 */
class Engine : ResponseListener {

    companion object {
        // 20M内存缓存
        const val MEMORY_CACHE_SIZE = 1024 * 1024 * 20
    }

    private lateinit var key: String
    private lateinit var path: String
    private lateinit var context: Context
    private lateinit var imageView: ImageView

    /**
     * weak reference cache
     * 当前活动页面引用资源
     */
    private val activeCache: ActiveCache by lazy {
        ActiveCache()
    }

    /**
     * lru memory cache
     * 不同页面对同一个资源多次引用
     */
    private val memoryCache: MemoryCache by lazy {
        MemoryCache(MEMORY_CACHE_SIZE)
    }

    /**
     * lru file cache
     * 磁盘缓存
     */
    private val diskCache: DiskLruCacheImpl by lazy {
        DiskLruCacheImpl()
    }

    fun load(path: String, context: Context) {
        this.key = Key(path).getKey()
        this.path = path
        this.context = context
    }

    fun into(imageView: ImageView): EngineResource? {
        this.imageView = imageView

        val cache = getCache()
        this.imageView.setImageBitmap(cache?.mBitmap)
        return cache
    }

    private fun getCache(): EngineResource? {
        // 1. active weak cache
        val activeCache = getActiveCache()
        if (activeCache != null) {
            return activeCache
        }
        // 2. Lru cache
        val memoryCache = getMemoryCache()
        if (memoryCache != null) {
            return memoryCache
        }
        // 3. file cache
        val fileCache = getFileCache()
        if (fileCache != null) {
            return fileCache
        }
        // 4. request net
        DataLoader().loadResource(path, context, this)
        return null
    }

    /**
     * try get active cache
     */
    private fun getActiveCache(): EngineResource? {
        val cache = activeCache.get(key)
        return cache?.also {
            it.reUse()
        }
    }

    /**
     * try get memory cache
     */
    private fun getMemoryCache(): EngineResource? {
        val engineResource = memoryCache.get(key)
        if (engineResource != null) {
            // 从内存缓存删除 放入活动缓存
            memoryCache.shouldRemove(key)
            activeCache.put(key, engineResource)
            engineResource.reUse()
        }
        return engineResource
    }

    /**
     * try get file cache
     */
    private fun getFileCache(): EngineResource? {
        val engineResource = diskCache.get(key)
        if (engineResource != null) {
            activeCache.put(key, engineResource)
            engineResource.reUse()
        }
        return engineResource
    }

    override fun onSuccess(engineResource: EngineResource) {
        engineResource.key = key
        // 1. 放入磁盘缓存
        diskCache.put(key, engineResource)
        // 2. 放入活动缓存
        activeCache.put(key, engineResource)
        // 3. +1
        engineResource.reUse()

        imageView.setImageBitmap(engineResource.mBitmap)
    }

    override fun onException(e: Throwable) {
        logE(e.message.toString())
    }

}