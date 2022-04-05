package com.akashi.roadmap.proxyPattern

/**
 * 角色：Subject
 */
interface IHttpProcessor {

    fun post(url: String, params: Map<String, Any>, callback: ICallback)
}