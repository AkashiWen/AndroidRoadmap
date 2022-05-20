package com.akashi.roadmap.utils

import android.view.Choreographer
import com.akashi.roadmap.common.logW


var lastFrameTimeNanos = 0L

/**
 * 丢失5帧
 */
const val DROPPED_FPS_5 = 5

fun startFPSMonitor() {
    Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            if (lastFrameTimeNanos == 0L) {
                lastFrameTimeNanos = frameTimeNanos
            } else {
                val diff = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000
                if (diff > 16.6F) {
                    // 掉帧了
                    val droppedCount = (diff / 16.6).toInt()
                    if (droppedCount > DROPPED_FPS_5) {
                        logW(
                            "UI线程超时（超过16ms）当前：${diff}ms，丢失：${droppedCount}帧",
                            "ChoreographerHelper"
                        )
                    }
                }
                lastFrameTimeNanos = frameTimeNanos
            }
            Choreographer.getInstance().postFrameCallback(this)
        }

    })
}