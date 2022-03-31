package com.akashi.roadmap.glide

import androidx.fragment.app.FragmentActivity
import com.akashi.roadmap.glide.request.RequestManager

class Glide private constructor(
) {

    lateinit var requestManager: RequestManager

    companion object {
        private val glide: Glide by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Glide()
        }

        private fun get(fragmentActivity: FragmentActivity): Glide {
            return glide.also {
                it.requestManager = RequestManager(fragmentActivity)
            }
        }

        fun with(fragmentActivity: FragmentActivity): RequestManager {
            return get(fragmentActivity).requestManager
        }
    }

}