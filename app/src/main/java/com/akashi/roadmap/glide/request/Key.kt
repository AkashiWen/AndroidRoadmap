package com.akashi.roadmap.glide.request

import com.akashi.roadmap.common.md5

class Key(private val path: String) {

    fun getKey(): String {
        // md5一下
        return md5(path)
    }
}