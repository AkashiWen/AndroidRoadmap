package com.akashi.road1.okhttp

import java.util.concurrent.*

/**
 * 最大同时请求数
 */
const val DEFAULT_MAX_REQUESTS = 64

/**
 * 一个HOST最多发起几个请求
 */
const val DEFAULT_MAX_HOST_REQUEST = 5

/**
 * call请求分发器
 * 分发到合适的队列，等待线程池调用
 */
class Dispatcher(
    private val maxRequests: Int = DEFAULT_MAX_REQUESTS,
    private val maxRequestHostRequest: Int = DEFAULT_MAX_HOST_REQUEST
) {

    /**
     * @see ArrayDeque
     * This class is likely to be faster than Stack when used as a stack,
     * and faster than LinkedList when used as a queue.
     *
     * 无限容量
     * 线程不安全
     * 不支持null
     */
    private val runningArrayDeque = ArrayDeque<Call.AsyncCall>()
    private val waitingArrayDeque = ArrayDeque<Call.AsyncCall>()

    private val executorService by lazy {
        Executors.newCachedThreadPool()
    }

    fun enqueue(asyncCall: Call.AsyncCall) {
        //  按照最大并发限制分发到等待队列，执行队列
        if (runningArrayDeque.size < maxRequests) {
            runningArrayDeque.add(asyncCall)
            executorService.submit(asyncCall)
        } else {
            waitingArrayDeque.add(asyncCall)
        }
    }

}