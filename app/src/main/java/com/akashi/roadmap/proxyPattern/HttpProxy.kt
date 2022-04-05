package com.akashi.roadmap.proxyPattern

/**
 * 单例
 * 角色：Proxy，代理
 */
class HttpProxy private constructor() : IHttpProcessor {

    companion object {
        /**
         * 本类单例
         */
        private val INSTANCE: HttpProxy by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpProxy()
        }

        /**
         * 源角色：realSubject，被代理角色
         */
        private var realHttpProcessor: IHttpProcessor? = null

        fun init(realHttpProcessor: IHttpProcessor) {
            this.realHttpProcessor = realHttpProcessor
        }

        fun obtain(): HttpProxy = INSTANCE
    }

    override fun post(url: String, params: Map<String, Any>, callback: ICallback) {
        realHttpProcessor?.post(url, params, callback)
    }

}