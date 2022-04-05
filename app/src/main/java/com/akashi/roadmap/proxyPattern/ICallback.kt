package com.akashi.roadmap.proxyPattern

interface ICallback {

    fun onSuccess(result: String)
    fun onFailure()
}