package com.akashi.road1.glide

import androidx.fragment.app.FragmentActivity
import com.akashi.road1.glide.request.RequestManager

class Glide private constructor() {

    companion object {
        fun with(fragmentActivity: FragmentActivity): RequestManager {
            return RequestManager(fragmentActivity)
        }
    }

}