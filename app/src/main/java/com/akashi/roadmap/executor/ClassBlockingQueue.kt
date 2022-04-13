package com.akashi.roadmap.executor

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

/**
 * 手写一个等待队列
 */
class ClassBlockingQueue<T>(size: Int) {

    private val queue = ArrayDeque<T>()

    private val lock = ReentrantLock()

    // 条件：队列满，程序往队列put动作等待
    private val fullWaitSet = lock.newCondition()

    // 条件：队列空，线程从队列take动作等待
    private val nullWaitSet = lock.newCondition()

    // 队列容量
    private val capacity = AtomicInteger(size)

    fun take(): T? {
        lock.lock()
        return try {
            while (queue.isEmpty()) nullWaitSet.await()
            val t = queue.removeFirst()
            fullWaitSet.signal()
            t
        } catch (e: InterruptedException) {
            e.printStackTrace()
            null
        } finally {
            lock.unlock()
        }
    }

    /**
     * 超时阻塞获取
     */
    fun poll(timeout: Long, unit: TimeUnit): T? {
        lock.lock()
        try {
            // 超时时间转纳秒
            var nanos = unit.toNanos(timeout)
            while (queue.isEmpty()) {
                if (nanos <= 0) {
                    return null
                }
                // 不断（消耗）减少nanos的值，直到小于0
                nanos = nullWaitSet.awaitNanos(nanos)
            }
            val t = queue.removeFirst()
            fullWaitSet.signal()
            return t
        } catch (e: InterruptedException) {
            return null
        } finally {
            lock.unlock()
        }
    }

    fun put(element: T) {
        lock.lock()
        try {
            while (queue.size >= capacity.toInt()) {
                println("对象: $element 等待中..")
                fullWaitSet.await()
            }
            queue.addLast(element)
            println("对象: $element 加入队列..")
            nullWaitSet.signal()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}