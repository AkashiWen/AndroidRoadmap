package com.akashi.road1.glide.request

import com.akashi.road1.common.md5

class Key(private val path: String) {

    fun getKey(): String {
        // md5一下
        return md5(path)
    }
}