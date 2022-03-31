package com.akashi.roadmap.glide.data

import android.graphics.Bitmap
import com.akashi.roadmap.common.logW

/**
 * 活动缓存，如果获取成功直接结束资源加载使用活动缓存
 */
data class EngineResource(
    /**
     * 位图
     */
    var mBitmap: Bitmap? = null,

    /**
     * 唯一标记
     */
    var key: String? = null,

    /**
     * 使用次数
     */
    var count: Int = 0
) {
    fun acquire() {
        if (mBitmap?.isRecycled == true) {
            logW("acquire(): bitmap已经被回收了, key=$key, count=$count")
            return
        }
        ++this.count
    }
}
