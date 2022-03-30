package com.akashi.roadmap.glide

import androidx.fragment.app.FragmentActivity
import com.akashi.roadmap.glide.request.RequestManager

class Glide private constructor() {

    companion object {
        fun with(fragmentActivity: FragmentActivity): RequestManager {
            return RequestManager(fragmentActivity)
        }
    }

}