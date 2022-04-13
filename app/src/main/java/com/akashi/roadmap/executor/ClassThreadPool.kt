package com.akashi.roadmap.executor

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * 手写一个线程池
 */
class ClassThreadPool(
    // 核心线程数
    private val coreSize: AtomicInteger,
    // 超时时间（队列没东西之后多久关闭线程池）
    private val timeout: Long,
    // 时间单位
    private val unit: TimeUnit = TimeUnit.SECONDS,
    // 最大任务数
    private val taskSize: Int = 10
) {


    // 任务队列
    private val taskQueue = ClassBlockingQueue<Runnable>(taskSize)

    // 线程容器
    private val workers = HashSet<Worker>()

    fun execute(task: Runnable) {
        // 在核心使用数范围内，直接run()
        if (workers.size <= coreSize.toInt()) {
            Worker(task).also {
                it.start()
            }.run {
                workers.add(this)
            }
        } else {
            // 超过核心使用数，加入等待队列
            taskQueue.put(task)
        }

    }

    inner class Worker(var task: Runnable?) : Thread() {

        override fun run() {
//            while (task != null || (taskQueue.take().also { task = it }) != null) {
            while (task != null || (taskQueue.poll(timeout, unit).also { task = it }) != null) {
                try {
                    task?.run()
                } finally {
                    task = null
                }
            }
        }
    }
}