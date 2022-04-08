package com.akashi.roadmap.annotation

/**
 * 用来绑定Activity
 */
interface IBinder<T> {
    fun bind(target: T)
}